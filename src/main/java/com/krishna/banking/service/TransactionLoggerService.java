package com.krishna.banking.service;

import com.krishna.banking.entity.Transaction;

public interface TransactionLoggerService {
    void logStatus(Transaction transaction);
}
