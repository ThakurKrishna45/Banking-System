package com.krishna.banking.repository;

import com.krishna.banking.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoanRepository extends JpaRepository<Loan,String> {
}
