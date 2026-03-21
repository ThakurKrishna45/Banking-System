package com.krishna.banking.service.impl;

import com.krishna.banking.config.CustomModelMapper;
import com.krishna.banking.entity.Account;
import com.krishna.banking.entity.Loan;
import com.krishna.banking.entity.dto.LoanDto;
import com.krishna.banking.entity.dto.ResponseLoanDto;
import com.krishna.banking.exception.InvalidOperationException;
import com.krishna.banking.exception.ResourceNotFoundException;
import com.krishna.banking.repository.AccountRepository;
import com.krishna.banking.repository.LoanRepository;
import com.krishna.banking.service.LoanService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    final private LoanRepository loanRepository;
    final private AccountRepository accountRepository;
    final private CustomModelMapper customModelMapper;
    @Override
    @Transactional
    public ResponseLoanDto applyLoan(LoanDto loanDto) {
        Integer accountId = loanDto.getAccountId();

        Account account = accountRepository.findById(accountId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found " + accountId));

        if (!account.isActive()) {
            throw new InvalidOperationException("Account is not active");
        }

        Integer customerId = account.getCustomer().getId();

        Integer activeLoans = loanRepository
                .countByAccount_Customer_IdAndStatus(customerId, "ACTIVE");

        if (activeLoans >= 3) {
            throw new InvalidOperationException("Maximum 3 active loans allowed");
        }

        BigDecimal maxLoan = account.getBalance().multiply(BigDecimal.valueOf(5));

        if (maxLoan.compareTo(loanDto.getAmount())<0) {
            throw new InvalidOperationException(
                    "Loan amount exceeds allowed limit (5x balance)");
        }

        Loan loan = new Loan();
        loan.setLoanId(UUID.randomUUID().toString());
        loan.setAmount(loanDto.getAmount());
        loan.setStatus("ACTIVE");
        loan.setAccount(account);

        Loan savedLoan = loanRepository.save(loan);
        return customModelMapper.toResponseDto(savedLoan);
    }

    @Override
    public List<ResponseLoanDto> getLoanByAccount(Integer id) {
        accountRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Account not found " + id));

        List<Loan> loanList = loanRepository.findByAccount_Id(id);
        return loanList.stream().map(l->customModelMapper.toResponseDto(l)).toList();
    }

    @Override
    public List<ResponseLoanDto> getApprovedLoans() {
        List<Loan> loanList = loanRepository.findByStatus("ACTIVE");
        return loanList.stream().map(l->customModelMapper.toResponseDto(l)).toList();
    }
}
