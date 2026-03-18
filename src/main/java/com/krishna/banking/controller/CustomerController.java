package com.krishna.banking.controller;

import com.krishna.banking.constants.AccountConstant;
import com.krishna.banking.entity.dto.CustomerDto;
import com.krishna.banking.entity.dto.ResponseCustomerDto;
import com.krishna.banking.entity.dto.ResponseDto;
import com.krishna.banking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class CustomerController {
    final private CustomerService customerService;
    @PostMapping("/createCustomer")
    public ResponseEntity<ResponseDto> createCustomer(@RequestBody CustomerDto customerDto){
        customerService.createCustomer(customerDto);
    return ResponseEntity.status(HttpStatus.CREATED).
            body(new ResponseDto(AccountConstant.STATUS_201,AccountConstant.MESSAGE_201));
    }
    @GetMapping("/getCustomerById/{id}")
    public ResponseEntity<ResponseDto> getCustomerById(@PathVariable Integer id){
        ResponseCustomerDto responseCustomerDto=customerService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseDto(AccountConstant.STATUS_200,AccountConstant.Message_get,responseCustomerDto));
    }
    @GetMapping("/getAllCustomer")
    public ResponseEntity<ResponseDto> getAllCustomer(){
        List<ResponseCustomerDto> responseCustomerDto=customerService.getAll();
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseDto(AccountConstant.STATUS_200,AccountConstant.Message_get,responseCustomerDto));
    }
    @DeleteMapping("/deleteCustomer")
    public ResponseEntity<ResponseDto>  deleteCustomer(@RequestParam Integer id){
        customerService.deleteCustomer(id);
        return ResponseEntity.status(HttpStatus.OK).
                body(new ResponseDto(AccountConstant.STATUS_204,AccountConstant.MESSAGE_204));
    }
}
