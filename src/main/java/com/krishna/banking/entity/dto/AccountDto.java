package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.AccountType;
import com.krishna.banking.entity.Customer;
import lombok.Data;

@Data
public class AccountDto {
    private AccountType accountType;
    private Integer customerId;
}
