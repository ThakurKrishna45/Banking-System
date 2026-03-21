package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.Account;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;
@Data
public class ResponseLoanDto {
    private String loanId;
    private BigDecimal amount;
    private String status;
    private Integer accountId;
}
