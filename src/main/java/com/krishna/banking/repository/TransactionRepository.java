package com.krishna.banking.repository;

import com.krishna.banking.entity.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {
    List<Transaction> findByAccount_IdOrderByDateDesc(Integer accountId, Pageable pageable);
}
