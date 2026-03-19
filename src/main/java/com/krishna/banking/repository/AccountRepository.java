package com.krishna.banking.repository;

import com.krishna.banking.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Integer> {

    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.customer WHERE a.id=:id")
    Optional<Account> findByIdAccount(@Param("id") Integer id);

    @Query("SELECT a FROM Account a LEFT JOIN FETCH a.customer c WHERE c.id=:id")
    List<Account> findByCustomerId(@Param("id") Integer id);
}
