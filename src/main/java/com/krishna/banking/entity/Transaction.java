package com.krishna.banking.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer txnId;
    private String referenceId;
    @Enumerated(EnumType.STRING)
    private TransactionType type;

    @Enumerated(EnumType.STRING)
    private TransactionDirection direction;

    private BigDecimal amount;
    @Enumerated(EnumType.STRING)
    private Status status;
    private LocalDateTime date;

    @ManyToOne
    private Account account;

    @ManyToOne
    private Account relatedAccount;
    @Builder
    public Transaction(String referenceId, TransactionType type, TransactionDirection direction,
                       BigDecimal amount, LocalDateTime date, Account account, Account relatedAccount,Status status) {
        this.referenceId = referenceId;
        this.type = type;
        this.direction = direction;
        this.amount = amount;
        this.date = date;
        this.account = account;
        this.relatedAccount = relatedAccount;
        this.status=status;
    }
}