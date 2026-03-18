package com.krishna.banking.service.impl;

import com.krishna.banking.repository.TransactionRepository;
import com.krishna.banking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TransactionServiceImpl implements TransactionService {
    final private TransactionRepository transactionRepository;
}
