package com.krishna.banking.config;

import com.krishna.banking.entity.Account;
import com.krishna.banking.entity.Customer;
import com.krishna.banking.entity.Loan;
import com.krishna.banking.entity.Transaction;
import com.krishna.banking.entity.dto.ResponseAccountDto;
import com.krishna.banking.entity.dto.ResponseCustomerDto;
import com.krishna.banking.entity.dto.ResponseLoanDto;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
@Component
public class CustomModelMapper {

    public ResponseCustomerDto mapCustomerResponse(Customer customer, ResponseCustomerDto dto) {
        dto.setName(customer.getName());
        dto.setEmail(customer.getEmail());
        List<Integer> ids = customer.getAccounts().stream()
                .map(Account::getId)
                .collect(Collectors.toList());
        dto.setAccountIds(ids);
        return dto;
    }
    public ResponseAccountDto mapAccountResponse(Account account, ResponseAccountDto responseAccountDto){
        responseAccountDto.setBalance(account.getBalance());
        responseAccountDto.setType(account.getAccountType());
        responseAccountDto.setCustomerId(account.getCustomer().getId());
        responseAccountDto.setActive(account.isActive());
        return responseAccountDto;
    }
    public ResponseTransactionDto mapTransactionResponse(Transaction transaction,ResponseTransactionDto responseDto){
        responseDto.setReferenceId(transaction.getReferenceId());
        responseDto.setType(String.valueOf(transaction.getType()));
        responseDto.setDirection(String.valueOf(transaction.getDirection()));
        responseDto.setAmount(transaction.getAmount());
        responseDto.setDate(transaction.getDate());
        responseDto.setAccountNumber(transaction.getAccount().getId());
        responseDto.setStatus(transaction.getStatus());
        if (transaction.getRelatedAccount() != null) {
            responseDto.setRelatedAccountNumber(transaction.getRelatedAccount().getId());
        }
        return responseDto;
    }
    public ResponseLoanDto toResponseDto(Loan loan) {

        ResponseLoanDto dto = new ResponseLoanDto();
        dto.setLoanId(loan.getLoanId());
        dto.setAmount(loan.getAmount());
        dto.setStatus(loan.getStatus());


            dto.setAccountId(loan.getAccount().getId());

        return dto;
    }
}
