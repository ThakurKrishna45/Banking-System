package com.krishna.banking.servicetest;

import com.krishna.banking.config.CustomModelMapper;
import com.krishna.banking.entity.Account;
import com.krishna.banking.entity.Transaction;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import com.krishna.banking.entity.dto.TransactionDto;
import com.krishna.banking.exception.ResourceNotFoundException;
import com.krishna.banking.repository.AccountRepository;
import com.krishna.banking.repository.TransactionRepository;
import com.krishna.banking.service.TransactionService;
import com.krishna.banking.service.impl.TransactionLoggerServiceImpl;
import com.krishna.banking.service.impl.TransactionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class TransactionServiceTest {
    @InjectMocks
    private TransactionServiceImpl transactionService;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private TransactionRepository transactionRepository;
    @Spy
    private ModelMapper modelMapper=new ModelMapper();
    @Mock
    private TransactionLoggerServiceImpl transactionLoggerService;
    @Spy
    private CustomModelMapper customModelMapper=new CustomModelMapper();
    private TransactionDto transactionDto;
    private Account account;
    @BeforeEach
    void setUp() {

        account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(15000));

        transactionDto = new TransactionDto();
        transactionDto.setAccountNumber(1);
        transactionDto.setAmount(BigDecimal.valueOf(10000));
        transactionDto.setRelatedAccountNumber(2);
    }
    @Test
    void testDeposit_Success() {

        when(accountRepository.findByIdWithLock(1)).thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseTransactionDto result = transactionService.deposit(transactionDto);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(25000), account.getBalance());
        verify(accountRepository, times(1)).findByIdWithLock(1);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }

    @Test
    void testDeposit_AccountNotFound() {

        when(accountRepository.findByIdWithLock(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            transactionService.deposit(transactionDto);
        });
        verify(transactionLoggerService, times(1)).logStatus(any(Transaction.class));
    }
    @Test
    void testWithdraw_Success() {

        when(accountRepository.findByIdWithLock(1)).thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i -> i.getArguments()[0]);

        ResponseTransactionDto result = transactionService.withdraw(transactionDto);

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(5000), account.getBalance());
        verify(accountRepository, times(1)).findByIdWithLock(1);
        verify(transactionRepository, times(1)).save(any(Transaction.class));
    }
    @Test
    void testTransfer(){
        Account related=new Account();
        related.setId(2);
        related.setBalance(BigDecimal.valueOf(5000));
        when(accountRepository.findByIdWithLock(1)).thenReturn(Optional.of(account));
        when(accountRepository.findByIdWithLock(2)).thenReturn(Optional.of(related));
        when(transactionRepository.save(any(Transaction.class))).thenAnswer(i->i.getArguments()[0]);
        ResponseTransactionDto transfer = transactionService.transfer(transactionDto);
        assertNotNull(transfer);
        assertEquals(BigDecimal.valueOf(5000),account.getBalance());
        assertEquals(BigDecimal.valueOf(15000),related.getBalance());
        verify(accountRepository, times(2)).findByIdWithLock(anyInt());
        verify(transactionRepository, times(2)).save(any(Transaction.class));
    }
}
