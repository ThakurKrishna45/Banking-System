package com.krishna.banking.service.impl;

import com.krishna.banking.entity.Transaction;
import com.krishna.banking.repository.TransactionRepository;
import com.krishna.banking.service.TransactionLoggerService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionLoggerServiceImpl implements TransactionLoggerService {
    @Autowired
    private TransactionRepository transactionRepository;
    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void logStatus(Transaction transaction) {
        transactionRepository.save(transaction);
    }
}