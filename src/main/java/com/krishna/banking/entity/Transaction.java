package com.krishna.banking.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer txnId;

    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;

    private double amount;

    private LocalDateTime date;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Account relatedAccount;
}