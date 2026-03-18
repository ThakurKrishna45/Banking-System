package com.krishna.banking.service;

import com.krishna.banking.entity.dto.CustomerDto;
import com.krishna.banking.entity.dto.ResponseCustomerDto;

import java.util.List;

public interface CustomerService {

    void createCustomer(CustomerDto customerDto);

    ResponseCustomerDto getById(Integer id);

    List<ResponseCustomerDto> getAll();

    void deleteCustomer(Integer id);
}
