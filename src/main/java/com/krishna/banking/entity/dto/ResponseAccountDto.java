package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.AccountType;

import java.math.BigDecimal;

public class ResponseAccountDto {
    private Integer id;
    private AccountType type;
    private BigDecimal balance;
    private Integer customerId;
}