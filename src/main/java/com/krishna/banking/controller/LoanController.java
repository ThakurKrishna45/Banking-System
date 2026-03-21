package com.krishna.banking.controller;

import com.krishna.banking.constants.AccountConstant;
import com.krishna.banking.entity.Loan;
import com.krishna.banking.entity.dto.LoanDto;
import com.krishna.banking.entity.dto.ResponseDto;
import com.krishna.banking.entity.dto.ResponseLoanDto;
import com.krishna.banking.service.LoanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loan")
@RequiredArgsConstructor
public class LoanController {
    final private LoanService loanService;
    @PostMapping("/applyLoan")
    public ResponseEntity<ResponseDto> applyLoan(@RequestBody LoanDto loanDto){
        ResponseLoanDto loan=loanService.applyLoan(loanDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201,AccountConstant.MESSAGE_201,loan));
    }
    @GetMapping("/getLoanByAccount/{id}")
    public ResponseEntity<ResponseDto> getLoanByAccount(@PathVariable Integer id){
        List<ResponseLoanDto> loan=loanService.getLoanByAccount(id);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201,AccountConstant.MESSAGE_201,loan));
    }
    @GetMapping("/getApprovedLoans")
    public ResponseEntity<ResponseDto> getApprovedLoans(){
        List<ResponseLoanDto> loan=loanService.getApprovedLoans();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201,AccountConstant.MESSAGE_201,loan));
    }
}
