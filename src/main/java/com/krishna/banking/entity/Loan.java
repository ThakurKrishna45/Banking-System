package com.krishna.banking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class Loan {
    @Id
    private String loanId;
    private double amount;
    private String status;
    @ManyToOne
    private Account account;
}