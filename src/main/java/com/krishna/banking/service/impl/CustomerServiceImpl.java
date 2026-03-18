package com.krishna.banking.service.impl;

import com.krishna.banking.config.CustomModelMapper;
import com.krishna.banking.entity.Customer;
import com.krishna.banking.entity.dto.CustomerDto;
import com.krishna.banking.entity.dto.ResponseCustomerDto;
import com.krishna.banking.exception.InvalidOperationException;
import com.krishna.banking.exception.ResourceNotFoundException;
import com.krishna.banking.repository.CustomerRepository;
import com.krishna.banking.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {
    final private CustomerRepository customerRepository;
    final private ModelMapper modelMapper;
    final private CustomModelMapper customModelMapper;

    @Override
    public void createCustomer(CustomerDto customerDto) {
        Optional<Customer> customerByEmail = customerRepository.findByEmail(customerDto.getEmail());
        if(customerByEmail.isPresent()){
            throw new InvalidOperationException("Email already Exist");
        }
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customerRepository.save(customer);
    }

    @Override
    public ResponseCustomerDto getById(Integer id) {
        Customer customer = customerRepository.findByIdWithAccounts(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer does not exist with id " + id));

        return customModelMapper.mapCustomerResponse(customer, new ResponseCustomerDto());
    }

    @Override
    public List<ResponseCustomerDto> getAll() {
        List<Customer> customers = customerRepository.findAllWithAccounts();
        List<ResponseCustomerDto> responseCustomerDtoList = customers.stream().
                map(c -> customModelMapper.
                        mapCustomerResponse(c, new ResponseCustomerDto())).toList();
        return responseCustomerDtoList;
    }

    @Override
    public void deleteCustomer(Integer id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Customer does not exist with id " + id));
        customerRepository.deleteById(id);
    }
}
