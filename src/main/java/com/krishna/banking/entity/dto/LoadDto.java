package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.Account;

import java.math.BigDecimal;

public class LoadDto {
    private String loadId;
    private BigDecimal amount;
    private Account account;
    private String status;
}
