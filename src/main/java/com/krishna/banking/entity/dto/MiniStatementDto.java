package com.krishna.banking.entity.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
@Data
public class MiniStatementDto {
        private String referenceId;
        private LocalDateTime date;
        private String type;
        private String direction;
        private BigDecimal amount;
}
