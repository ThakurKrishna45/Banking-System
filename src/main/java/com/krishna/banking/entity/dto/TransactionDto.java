package com.krishna.banking.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private BigDecimal amount;
    private Integer accountNumber;
    private Integer relatedAccountNumber;
}
