package com.krishna.banking.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseDto {
    private String statusCode;
    private String statusMessage;
    private Object object;

    public ResponseDto(String statusMessage, String statusCode) {
        this.statusMessage = statusMessage;
        this.statusCode = statusCode;
    }
}
