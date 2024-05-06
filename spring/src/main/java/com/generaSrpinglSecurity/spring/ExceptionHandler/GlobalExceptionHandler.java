package com.generaSrpinglSecurity.spring.ExceptionHandler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.Collections;

@ControllerAdvice
@ResponseStatus
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponse> HandleUsernameNotFoundException(UsernameNotFoundException exception, WebRequest request){
    LocalDateTime timeStamp = LocalDateTime.now();
    ErrorResponse message = new ErrorResponse(timeStamp, HttpStatus.NOT_FOUND, Collections.singletonList(exception.getMessage()));
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
            .body(message);
}

    @ExceptionHandler(UserNameExistException.class)
    @ResponseStatus(HttpStatus.FOUND)
    public ResponseEntity<ErrorResponse> handleUserNameExistException(UserNameExistException exception, WebRequest request){
        LocalDateTime timestamp = LocalDateTime.now();
        ErrorResponse message = new ErrorResponse(timestamp, HttpStatus.FOUND, Collections.singletonList(exception.getMessage()));
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(message);
    }




}
