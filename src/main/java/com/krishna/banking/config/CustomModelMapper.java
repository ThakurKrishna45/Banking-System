package com.krishna.banking.config;

import com.krishna.banking.entity.Account;
import com.krishna.banking.entity.Customer;
import com.krishna.banking.entity.dto.ResponseAccountDto;
import com.krishna.banking.entity.dto.ResponseCustomerDto;
import org.springframework.stereotype.Component;

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
}
