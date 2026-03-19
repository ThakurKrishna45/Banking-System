package com.krishna.banking.service.impl;

import com.krishna.banking.config.CustomModelMapper;
import com.krishna.banking.entity.Account;
import com.krishna.banking.entity.Customer;
import com.krishna.banking.entity.dto.AccountDto;
import com.krishna.banking.entity.dto.ResponseAccountDto;
import com.krishna.banking.exception.InvalidOperationException;
import com.krishna.banking.exception.ResourceNotFoundException;
import com.krishna.banking.repository.AccountRepository;
import com.krishna.banking.repository.CustomerRepository;
import com.krishna.banking.service.AccountService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final CustomModelMapper customModelMapper;
    private final CustomerRepository customerRepository;
    @Override

    public void createAccount(AccountDto accountDto) {

        Customer customer = customerRepository.findById(accountDto.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found with id: " + accountDto.getCustomerId()));

        Account account = new Account();

        account.setAccountType(accountDto.getAccountType());
        account.setCustomer(customer);

        Account savedAccount = accountRepository.save(account);

        if (savedAccount.getId() == null) {
            throw new InvalidOperationException("Failed to save the account");
        }
    }

    @Override
    public ResponseAccountDto getAccountById(Integer id) {
        Account account = accountRepository.findByIdAccount(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account does not exist with id" + id));
        return customModelMapper.mapAccountResponse(account,new ResponseAccountDto());
    }

    @Override
    public List<ResponseAccountDto> getAccountByCustomer(Integer id) {
        List<Account> accountList = accountRepository.findByCustomerId(id);
        if(accountList.isEmpty())throw new ResourceNotFoundException("No account exist with customerId "+id);
        return accountList.stream().
                map(a->customModelMapper.mapAccountResponse(a,new ResponseAccountDto())).toList();
    }

    @Override
    public void deactivateAccount(Integer id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Account does not exist with id" + id));
        account.setActive(false);
        accountRepository.save(account);
    }
}
