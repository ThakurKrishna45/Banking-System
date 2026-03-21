package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.Account;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class LoanDto {
    private BigDecimal amount;
    private Integer accountId;
}
