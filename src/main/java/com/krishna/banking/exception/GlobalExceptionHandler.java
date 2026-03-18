package com.krishna.banking.exception;

//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.context.request.WebRequest;
//
//import java.time.LocalDateTime;
//
//public class GlobalExceptionHandler {
//import java.time.LocalDateTime;
//
//    @ControllerAdvice
//    public class GlobalExceptionHandler {
//        public ResponseEntity<ErrorResponseDto> handleAccountNotFoundException(AccountNotFoundException accountNotFoundException, WebRequest webRequest){
//            ErrorResponseDto errorResponseDto=new ErrorResponseDto(
//                    webRequest.getDescription(false), HttpStatus.BAD_REQUEST, accountNotFoundException.getMessage(), LocalDateTime.now());
//            return new ResponseEntity<>(errorResponseDto,HttpStatus.BAD_REQUEST);
//        }
//    }
//}
