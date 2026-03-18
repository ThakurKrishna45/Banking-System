package com.krishna.banking.repository;

import com.krishna.banking.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends JpaRepository<Customer,Integer> {
    Optional findByEmail(String email);

    @Query("SELECT c FROM Customer c LEFT JOIN FETCH c.accounts WHERE c.id = :id")
    Optional<Customer> findByIdWithAccounts(@Param("id") Integer id);

    @Query("SELECT DISTINCT c FROM Customer c LEFT JOIN FETCH c.accounts")
    List<Customer> findAllWithAccounts();
}
