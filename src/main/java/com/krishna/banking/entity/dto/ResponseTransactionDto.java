package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class ResponseTransactionDto {

    private String referenceId;
    private String type;
    private String direction;

    private BigDecimal amount;
    private LocalDateTime date;

    private Integer accountNumber;
    private Integer relatedAccountNumber;
    private Status status;
}
