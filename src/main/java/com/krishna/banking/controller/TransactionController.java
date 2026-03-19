package com.krishna.banking.controller;

import com.krishna.banking.constants.AccountConstant;
import com.krishna.banking.entity.dto.MiniStatementDto;
import com.krishna.banking.entity.dto.ResponseDto;
import com.krishna.banking.entity.dto.ResponseTransactionDto;
import com.krishna.banking.entity.dto.TransactionDto;
import com.krishna.banking.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/transaction")
@RequiredArgsConstructor
public class TransactionController {
    final private TransactionService transactionService;
    @PostMapping("/deposit")
    public ResponseEntity<ResponseDto> deposit(@RequestBody TransactionDto transactionDto){
        ResponseTransactionDto responseTransactionDto= transactionService.deposit(transactionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(AccountConstant.STATUS_200,"Money Deposited",responseTransactionDto));
    }
    @PostMapping("/withdraw")
    public ResponseEntity<ResponseDto> withdraw(@RequestBody TransactionDto transactionDto){
        ResponseTransactionDto responseTransactionDto= transactionService.withdraw(transactionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(AccountConstant.STATUS_200,"Money Withdrawn",responseTransactionDto));
    }
    @PostMapping("/transfer")
    public ResponseEntity<ResponseDto> transfer(@RequestBody TransactionDto transactionDto){
        ResponseTransactionDto responseTransactionDto= transactionService.transfer(transactionDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(AccountConstant.STATUS_200,"Money Transfer Successful",responseTransactionDto));
    }
    @GetMapping("/miniStatement/{id}")
    public ResponseEntity<ResponseDto> miniStatement(@PathVariable Integer id){
        List<MiniStatementDto> miniStatement= transactionService.miniStatement(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDto(AccountConstant.STATUS_200,AccountConstant.Message_get,miniStatement));
    }
}
