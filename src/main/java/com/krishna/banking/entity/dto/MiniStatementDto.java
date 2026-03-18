package com.krishna.banking.entity.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class MiniStatementDto {
        private LocalDateTime date;
        private String type;
        private String direction;
        private BigDecimal amount;
}
