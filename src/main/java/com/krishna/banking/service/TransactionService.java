package com.krishna.banking.service;

import com.krishna.banking.entity.dto.MiniStatementDto;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import com.krishna.banking.entity.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    ResponseTransactionDto deposit(TransactionDto transactionDto,
                                   String idempotencyKey);

    ResponseTransactionDto withdraw(TransactionDto transactionDto,
                                    String idempotencyKey);

    ResponseTransactionDto transfer(TransactionDto transactionDto,
                                    String idempotencyKey);

    List<MiniStatementDto> miniStatement(Integer id);
}
