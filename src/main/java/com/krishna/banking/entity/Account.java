package com.krishna.banking.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Data
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    private BigDecimal balance= BigDecimal.valueOf(0);
    private boolean isActive=true;

    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL)
    private List<Loan> loans;
    @OneToMany(mappedBy = "account")
    private List<Transaction> transactions;
}
