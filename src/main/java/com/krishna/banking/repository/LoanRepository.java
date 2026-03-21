package com.krishna.banking.repository;

import com.krishna.banking.entity.Loan;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan,String> {
    Integer countByAccount_Customer_IdAndStatus(Integer customerId, String active);

    List<Loan> findByAccount_Id(Integer id);

    List<Loan> findByStatus(String active);
}
