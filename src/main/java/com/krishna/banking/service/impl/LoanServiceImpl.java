package com.krishna.banking.service.impl;

import com.krishna.banking.repository.LoanRepository;
import com.krishna.banking.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LoanServiceImpl implements LoanService {
    final private LoanRepository loanRepository;
}
