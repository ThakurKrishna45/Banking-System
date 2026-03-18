package com.krishna.banking.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;

@Entity
@Data
public class Loan {
    @Id
    private String loanId;
    private BigDecimal amount;
    private String status="ACTIVE";
    @ManyToOne
    private Account account;
}