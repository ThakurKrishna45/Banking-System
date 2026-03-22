package com.krishna.banking.service;

import com.krishna.banking.entity.dto.AccountDto;
import com.krishna.banking.entity.dto.ResponseAccountDto;

import java.util.List;

public interface AccountService {
    void createAccount(AccountDto accountDto);

    ResponseAccountDto getAccountById(Integer id);

    List<ResponseAccountDto> getAccountByCustomer(Integer id);

    void deactivateAccount(Integer id);

    void ActivateAccount(Integer id);
}
