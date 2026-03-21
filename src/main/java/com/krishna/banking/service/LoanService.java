package com.krishna.banking.service;

import com.krishna.banking.entity.dto.LoanDto;
import com.krishna.banking.entity.dto.ResponseLoanDto;

import java.util.List;

public interface LoanService {
    ResponseLoanDto applyLoan(LoanDto loanDto);

    List<ResponseLoanDto> getLoanByAccount(Integer id);

    List<ResponseLoanDto> getApprovedLoans();
}
