package com.krishna.banking.service;

import com.krishna.banking.entity.dto.MiniStatementDto;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import com.krishna.banking.entity.dto.TransactionDto;

import java.util.List;

public interface TransactionService {
    ResponseTransactionDto deposit(TransactionDto transactionDto);

    ResponseTransactionDto withdraw(TransactionDto transactionDto);

    ResponseTransactionDto transfer(TransactionDto transactionDto);

    List<MiniStatementDto> miniStatement(Integer id);
}
