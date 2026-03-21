package com.krishna.banking.service.impl;

import com.krishna.banking.config.CustomModelMapper;
import com.krishna.banking.entity.Account;
import com.krishna.banking.entity.Transaction;
import com.krishna.banking.entity.TransactionDirection;
import com.krishna.banking.entity.TransactionType;
import com.krishna.banking.entity.dto.MiniStatementDto;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import com.krishna.banking.entity.dto.TransactionDto;
import com.krishna.banking.exception.InvalidOperationException;
import com.krishna.banking.exception.ResourceNotFoundException;
import com.krishna.banking.repository.AccountRepository;
import com.krishna.banking.repository.TransactionRepository;
import com.krishna.banking.service.TransactionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import org.modelmapper.ModelMapper;
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
    @Override
    @Transactional
    public ResponseTransactionDto deposit(TransactionDto transactionDto) {
        Integer accountNumber=transactionDto.getAccountNumber();
        Account account = accountRepository.findByIdWithLock(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account does not exist with account number" + accountNumber));
        account.setBalance(account.getBalance().add(transactionDto.getAmount()));


        String refId = UUID.randomUUID().toString();
        Transaction credit = new Transaction();
        credit.setReferenceId(refId);
        credit.setAccount(account);
        credit.setDirection(TransactionDirection.CREDIT);
        credit.setType(TransactionType.DEPOSIT);
        credit.setAmount(transactionDto.getAmount());
        credit.setDate(LocalDateTime.now());

        transactionRepository.save(credit);

        return customModelMapper.mapTransactionResponse(credit,new ResponseTransactionDto());
    }

    @Override
    @Transactional
    public ResponseTransactionDto withdraw(TransactionDto transactionDto) {
        Integer accountNumber=transactionDto.getAccountNumber();
        BigDecimal amount=transactionDto.getAmount();

        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException("Amount must be greater than zero");
        }
        Account account = accountRepository.findByIdWithLock(accountNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Account does not exist with account number" + accountNumber));

        if(account.getBalance().compareTo(amount)<0) throw new InvalidOperationException("Insufficient balance");
        account.setBalance(account.getBalance().subtract(amount));


        String refId = UUID.randomUUID().toString();
        Transaction debit = new Transaction();
        debit.setReferenceId(refId);
        debit.setAccount(account);
        debit.setDirection(TransactionDirection.DEBIT);
        debit.setType(TransactionType.WITHDRAW);
        debit.setAmount(amount);
        debit.setDate(LocalDateTime.now());

        transactionRepository.save(debit);

        return customModelMapper.mapTransactionResponse(debit,new ResponseTransactionDto());
    }

    @Override
    @Transactional
    public ResponseTransactionDto transfer(TransactionDto transactionDto) {
        Integer senderAccountNumber=transactionDto.getAccountNumber();
        Integer receiverAccountNumber=transactionDto.getRelatedAccountNumber();
        BigDecimal amount=transactionDto.getAmount();
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new InvalidOperationException("Amount must be greater than zero");
        }
        Integer firstId = Math.min(senderAccountNumber, receiverAccountNumber);
        Integer secondId = Math.max(senderAccountNumber, receiverAccountNumber);

        Account first = accountRepository.findByIdWithLock(firstId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account does not exist with account number " + firstId));

        Account second = accountRepository.findByIdWithLock(secondId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account does not exist with account number " + secondId));

        // identify sender and receiver
        Account sender = senderAccountNumber.equals(firstId) ? first : second;
        Account receiver = receiverAccountNumber.equals(secondId) ? second : first;

        if(sender.getBalance().compareTo(amount)<0) throw new InvalidOperationException("Insufficient balance");


        String refId = UUID.randomUUID().toString();

        // for sender
        sender.setBalance(sender.getBalance().subtract(amount));
        Transaction debit = new Transaction();
        debit.setReferenceId(refId);
        debit.setAccount(sender);
        debit.setDirection(TransactionDirection.DEBIT);
        debit.setType(TransactionType.TRANSFER);
        debit.setAmount(amount);
        debit.setDate(LocalDateTime.now());
        debit.setRelatedAccount(receiver);
        transactionRepository.save(debit);

        //  for receiver
        receiver.setBalance(receiver.getBalance().add(transactionDto.getAmount()));
        Transaction credit = new Transaction();
        credit.setReferenceId(refId);
        credit.setAccount(receiver);
        credit.setDirection(TransactionDirection.CREDIT);
        credit.setType(TransactionType.DEPOSIT);
        credit.setAmount(transactionDto.getAmount());
        credit.setDate(LocalDateTime.now());
        credit.setRelatedAccount(sender);
        transactionRepository.save(credit);
        return customModelMapper.mapTransactionResponse(debit,new ResponseTransactionDto());
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

