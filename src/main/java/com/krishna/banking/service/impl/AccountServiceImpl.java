package com.krishna.banking.service.impl;

import com.krishna.banking.repository.AccountRepository;
import com.krishna.banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
}
