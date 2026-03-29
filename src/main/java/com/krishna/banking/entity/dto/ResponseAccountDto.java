package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.AccountType;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
@Data
public class ResponseAccountDto implements Serializable {
    private AccountType type;
    private BigDecimal balance;
    private Integer customerId;
    private boolean isActive;
}