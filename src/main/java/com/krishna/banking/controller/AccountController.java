package com.krishna.banking.controller;

import com.krishna.banking.constants.AccountConstant;
import com.krishna.banking.entity.dto.AccountDto;
import com.krishna.banking.entity.dto.ResponseAccountDto;
import com.krishna.banking.entity.dto.ResponseDto;
import com.krishna.banking.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {
    final private AccountService accountService;
    @PostMapping("/createAccount")
    public ResponseEntity<ResponseDto> createAccount(@RequestBody AccountDto accountDto){
        accountService.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseDto(AccountConstant.STATUS_201,AccountConstant.MESSAGE_201));
    }
    @GetMapping("/getAccountById/{id}")
    public ResponseEntity<ResponseDto> getAccountById(@PathVariable Integer id){
        ResponseAccountDto responseAccountDto=accountService.getAccountById(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ResponseDto(AccountConstant.STATUS_200,AccountConstant.Message_get,responseAccountDto));
    }

    @GetMapping("/getAccountByCusotmer")
    public ResponseEntity<ResponseDto> getAccountByCustomer(@RequestParam Integer id){
        List<ResponseAccountDto> responseAccountDto=accountService.getAccountByCustomer(id);
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(new ResponseDto(AccountConstant.STATUS_200,AccountConstant.Message_get,responseAccountDto));
    }
    @PatchMapping("deactivateAccount/{id}")
    public ResponseEntity<String> deactivateAccount(@PathVariable Integer id){
        accountService.deactivateAccount(id);
        return ResponseEntity.ok("Account Deactivated Successfully");
    }
}
