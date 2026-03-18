package com.krishna.banking.entity.dto;

import com.krishna.banking.entity.Account;
import lombok.Data;

import java.util.List;
@Data
public class ResponseCustomerDto {
    private String name;
    private String email;
    private List<Integer> accountIds;;
}
