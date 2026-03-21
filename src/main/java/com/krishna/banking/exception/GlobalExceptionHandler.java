package com.krishna.banking.exception;

import com.krishna.banking.entity.dto.ErrorResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

    @ControllerAdvice
    public class GlobalExceptionHandler {

        @ExceptionHandler(ResourceNotFoundException.class)
        public ResponseEntity<ErrorResponseDto> handleAccountNotFoundException(
                ResourceNotFoundException exception, WebRequest webRequest) {

            ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                    webRequest.getDescription(false),
                    HttpStatus.NOT_FOUND,
                    exception.getMessage(),
                    LocalDateTime.now());

            return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
        }

        @ExceptionHandler(InvalidOperationException.class)
        public ResponseEntity<ErrorResponseDto> handleInvalidOperationException(
                InvalidOperationException exception, WebRequest webRequest) {

            ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                    webRequest.getDescription(false),
                    HttpStatus.BAD_REQUEST,
                    exception.getMessage(),
                    LocalDateTime.now());

            return new ResponseEntity<>(errorResponseDto, HttpStatus.BAD_REQUEST);
        }
        @ExceptionHandler(Exception.class)
        public ResponseEntity<ErrorResponseDto> handleGlobalException(
                Exception exception, WebRequest webRequest) {

            ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                    webRequest.getDescription(false),
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    "An unexpected error occurred: " + exception.getMessage(),
                    LocalDateTime.now());

            return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}
