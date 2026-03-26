package com.krishna.banking.controllertest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.krishna.banking.controller.TransactionController;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import com.krishna.banking.entity.dto.TransactionDto;
import com.krishna.banking.service.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TransactionController.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private TransactionService transactionService;
    @Autowired
    private ObjectMapper objectMapper;
    private TransactionDto transactionDto;
    private ResponseTransactionDto responseDto;

    @BeforeEach
    void setUp() {

        transactionDto = new TransactionDto();
        transactionDto.setAmount(BigDecimal.valueOf(10000));
        transactionDto.setAccountNumber(1);

        responseDto = new ResponseTransactionDto();
        responseDto.setAmount(BigDecimal.valueOf(10000));
    }
    @Test
    void testDeposit() throws Exception{

        when(transactionService.deposit(any(TransactionDto.class))).thenReturn(responseDto);
        mockMvc.perform(post("/transaction/deposit")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isOk());
    }
    @Test
    void testWithdraw() throws Exception{

        when(transactionService.withdraw(any(TransactionDto.class))).thenReturn(responseDto);
        mockMvc.perform(post("/transaction/withdraw")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isOk());
    }
    @Test
    void testTransfer() throws Exception{

        when(transactionService.transfer(any(TransactionDto.class))).thenReturn(responseDto);
        mockMvc.perform(post("/transaction/transfer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(transactionDto)))
                .andExpect(status().isOk());
    }
}
