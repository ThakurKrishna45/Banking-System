package com.krishna.banking.service.impl;

import com.krishna.banking.config.CustomModelMapper;
import com.krishna.banking.entity.*;
import com.krishna.banking.entity.dto.MiniStatementDto;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import com.krishna.banking.entity.dto.TransactionDto;
import com.krishna.banking.exception.InvalidOperationException;
import com.krishna.banking.exception.ResourceNotFoundException;
import com.krishna.banking.repository.AccountRepository;
import com.krishna.banking.repository.TransactionRepository;
import com.krishna.banking.service.TransactionLoggerService;
import com.krishna.banking.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Caching;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    final private TransactionRepository transactionRepository;
    final private AccountRepository accountRepository;
    final private CustomModelMapper customModelMapper;
    final private ModelMapper modelMapper;
    final private TransactionLoggerService transactionLoggerService;
        private Transaction buildTxn(String refId, Account account, Account relatedAccount,
                                     BigDecimal amt, TransactionType type,
                                     TransactionDirection dir, Status status) {
            return Transaction.builder()
                    .referenceId(refId)
                    .account(account)
                    .relatedAccount(relatedAccount)
                    .amount(amt)
                    .type(type)
                    .direction(dir)
                    .date(LocalDateTime.now())
                    .status(status)
                    .build();
        }

        @Override
        @Transactional
        @CacheEvict(value = "Account", key = "#dto.getAccountNumber()")
        public ResponseTransactionDto deposit(TransactionDto dto) {
            String refId = UUID.randomUUID().toString();
            Account account = null;
            try {
                account = accountRepository.findByIdWithLock(dto.getAccountNumber())
                        .orElseThrow(() -> new ResourceNotFoundException("Account not found with account number "+dto.getAccountNumber()));

                account.setBalance(account.getBalance().add(dto.getAmount()));

                Transaction success = buildTxn(refId, account, null, dto.getAmount(),
                        TransactionType.DEPOSIT, TransactionDirection.CREDIT, Status.SUCCESS);
                transactionRepository.save(success);
                return customModelMapper.mapTransactionResponse(success, new ResponseTransactionDto());

            } catch (Exception e) {
                transactionLoggerService.logStatus(buildTxn(refId, account, null, dto.getAmount(),
                        TransactionType.DEPOSIT, TransactionDirection.CREDIT, Status.FAILED));
                throw e;
            }
        }

        @Override
        @Transactional
        @CacheEvict(value = "Account", key = "#dto.getAccountNumber()")
        public ResponseTransactionDto withdraw(TransactionDto dto) {
            String refId = UUID.randomUUID().toString();
            Account account = null;
            try {
                if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) throw new InvalidOperationException("Invalid amount");

                account = accountRepository.findByIdWithLock(dto.getAccountNumber())
                        .orElseThrow(() -> new ResourceNotFoundException("Account not found with account number "+dto.getAccountNumber()));

                if(account.getBalance().compareTo(dto.getAmount()) < 0) throw new InvalidOperationException("Insufficient balance");

                account.setBalance(account.getBalance().subtract(dto.getAmount()));

                Transaction success = buildTxn(refId, account, null, dto.getAmount(),
                        TransactionType.WITHDRAW, TransactionDirection.DEBIT, Status.SUCCESS);
                transactionRepository.save(success);
                return customModelMapper.mapTransactionResponse(success, new ResponseTransactionDto());

            } catch (Exception e) {
                transactionLoggerService.logStatus(buildTxn(refId, account, null, dto.getAmount(),
                        TransactionType.WITHDRAW, TransactionDirection.DEBIT, Status.FAILED));
                throw e;
            }
        }

        @Override
        @Transactional
        @Caching(evict = {
                @CacheEvict(value = "Account", key = "#dto.getAccountNumber()"),
                @CacheEvict(value = "Account", key = "#dto.getRelatedAccountNumber()")
        })
        public ResponseTransactionDto transfer(TransactionDto dto) {
            String refId = UUID.randomUUID().toString();
            Account sender = null;
            try {
                if (dto.getAmount().compareTo(BigDecimal.ZERO) <= 0) throw new InvalidOperationException("Invalid amount");

                // Pessimistic Locking in order to prevent deadlocks
                Integer firstId = Math.min(dto.getAccountNumber(), dto.getRelatedAccountNumber());
                Integer secondId = Math.max(dto.getAccountNumber(), dto.getRelatedAccountNumber());

                Account first = accountRepository.findByIdWithLock(firstId).orElseThrow(() -> new ResourceNotFoundException("Account not found with account number "+firstId));
                Account second = accountRepository.findByIdWithLock(secondId).orElseThrow(() -> new ResourceNotFoundException("Account not with account number "+secondId));

                sender = dto.getAccountNumber().equals(firstId) ? first : second;
                Account receiver = dto.getRelatedAccountNumber().equals(secondId) ? second : first;

                if(sender.getBalance().compareTo(dto.getAmount()) < 0) throw new InvalidOperationException("Insufficient balance");

                // Perform Money Movement
                sender.setBalance(sender.getBalance().subtract(dto.getAmount()));
                receiver.setBalance(receiver.getBalance().add(dto.getAmount()));

                // Save Debit Record for Sender
                Transaction debit = buildTxn(refId, sender, receiver, dto.getAmount(),
                        TransactionType.TRANSFER, TransactionDirection.DEBIT, Status.SUCCESS);
                transactionRepository.save(debit);

                // Save Credit Record for Receiver
                Transaction credit = buildTxn(refId, receiver, sender, dto.getAmount(),
                        TransactionType.TRANSFER, TransactionDirection.CREDIT, Status.SUCCESS);
                transactionRepository.save(credit);

                return customModelMapper.mapTransactionResponse(debit, new ResponseTransactionDto());

            } catch (Exception e) {

                transactionLoggerService.logStatus(buildTxn(refId, sender, null, dto.getAmount(),
                        TransactionType.TRANSFER, TransactionDirection.DEBIT, Status.FAILED));
                throw e;
            }
        }
        @Override
        public List<MiniStatementDto> miniStatement(Integer id) {
        accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found " + id));
        Pageable limit = PageRequest.of(0, 10);
        List<Transaction> transactionList = transactionRepository.findByAccount_IdOrderByDateDesc(id, limit);

        return transactionList.stream().map(t->modelMapper.map(t,MiniStatementDto.class)).toList();
    }
}

