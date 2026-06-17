package com.krishna.banking.servicetest;

import com.krishna.banking.config.CustomModelMapper;
import com.krishna.banking.entity.Account;
import com.krishna.banking.entity.Transaction;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import com.krishna.banking.entity.dto.TransactionDto;
import com.krishna.banking.exception.ResourceNotFoundException;
import com.krishna.banking.kafka.ProducerService;
import com.krishna.banking.repository.AccountRepository;
import com.krishna.banking.repository.TransactionRepository;
import com.krishna.banking.service.impl.IdempotencyService;
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
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    private TransactionServiceImpl transactionService;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private IdempotencyService idempotencyService;

    @Mock
    private ProducerService producerService;

    @Spy
    private ModelMapper modelMapper = new ModelMapper();

    @Mock
    private TransactionLoggerServiceImpl transactionLoggerService;

    @Spy
    private CustomModelMapper customModelMapper = new CustomModelMapper();

    private TransactionDto transactionDto;
    private Account account;

    private static final String IDEMPOTENCY_KEY =
            "test-idempotency-key";

    @BeforeEach
    void setUp() {

        account = new Account();
        account.setId(1);
        account.setBalance(BigDecimal.valueOf(15000));

        transactionDto = new TransactionDto();
        transactionDto.setAccountNumber(1);
        transactionDto.setAmount(BigDecimal.valueOf(10000));
        transactionDto.setRelatedAccountNumber(2);

        when(idempotencyService.acquire(anyString()))
                .thenReturn(true);
    }

    @Test
    void testDeposit_Success() {

        when(accountRepository.findByIdWithLock(1))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ResponseTransactionDto result =
                transactionService.deposit(
                        transactionDto,
                        IDEMPOTENCY_KEY
                );

        assertNotNull(result);
        assertEquals(
                BigDecimal.valueOf(25000),
                account.getBalance()
        );

        verify(idempotencyService)
                .acquire(IDEMPOTENCY_KEY);

        verify(accountRepository)
                .findByIdWithLock(1);

        verify(transactionRepository)
                .save(any(Transaction.class));

        verify(idempotencyService)
                .success(eq(IDEMPOTENCY_KEY), anyString());
    }

    @Test
    void testDeposit_AccountNotFound() {

        when(accountRepository.findByIdWithLock(1))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> transactionService.deposit(
                        transactionDto,
                        IDEMPOTENCY_KEY
                )
        );

        verify(idempotencyService)
                .failed(IDEMPOTENCY_KEY);

        verify(transactionLoggerService)
                .logStatus(any(Transaction.class));
    }

    @Test
    void testWithdraw_Success() {

        when(accountRepository.findByIdWithLock(1))
                .thenReturn(Optional.of(account));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ResponseTransactionDto result =
                transactionService.withdraw(
                        transactionDto,
                        IDEMPOTENCY_KEY
                );

        assertNotNull(result);

        assertEquals(
                BigDecimal.valueOf(5000),
                account.getBalance()
        );

        verify(idempotencyService)
                .acquire(IDEMPOTENCY_KEY);

        verify(accountRepository)
                .findByIdWithLock(1);

        verify(transactionRepository)
                .save(any(Transaction.class));

        verify(idempotencyService)
                .success(eq(IDEMPOTENCY_KEY), anyString());
    }

    @Test
    void testTransfer_Success() {

        Account related = new Account();
        related.setId(2);
        related.setBalance(BigDecimal.valueOf(5000));

        when(accountRepository.findByIdWithLock(1))
                .thenReturn(Optional.of(account));

        when(accountRepository.findByIdWithLock(2))
                .thenReturn(Optional.of(related));

        when(transactionRepository.save(any(Transaction.class)))
                .thenAnswer(i -> i.getArguments()[0]);

        ResponseTransactionDto result =
                transactionService.transfer(
                        transactionDto,
                        IDEMPOTENCY_KEY
                );

        assertNotNull(result);

        assertEquals(
                BigDecimal.valueOf(5000),
                account.getBalance()
        );

        assertEquals(
                BigDecimal.valueOf(15000),
                related.getBalance()
        );

        verify(idempotencyService)
                .acquire(IDEMPOTENCY_KEY);

        verify(accountRepository, times(2))
                .findByIdWithLock(anyInt());

        verify(transactionRepository, times(2))
                .save(any(Transaction.class));

        verify(idempotencyService)
                .success(eq(IDEMPOTENCY_KEY), anyString());
    }
}