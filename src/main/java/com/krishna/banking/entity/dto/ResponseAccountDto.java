package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.AccountType;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ResponseAccountDto {
    private AccountType type;
    private BigDecimal balance;
    private Integer customerId;
    private boolean isActive;
}