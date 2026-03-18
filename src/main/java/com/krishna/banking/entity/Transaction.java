package com.krishna.banking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer txnId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;

    private BigDecimal amount;

    private LocalDateTime date;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Account relatedAccount;
}