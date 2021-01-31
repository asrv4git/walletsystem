package com.fabhotels.walletsystem.controller.exceptioncontroller;

import com.fabhotels.walletsystem.exceptions.InsufficientBalanceException;
import com.fabhotels.walletsystem.exceptions.UserAlreadyExistsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;

@ControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ApiErrorController {

    private static final Logger log = LoggerFactory.getLogger(ApiErrorController.class);

    //used to throw exceptions from anywhere
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity handleResponseStatusException(ResponseStatusException ex) {
        loggingOfExceptions(ex);
        return ResponseEntity.status(ex.getStatus()).build();
    }

    //catch any exception
    @ExceptionHandler(Exception.class)
    public ResponseEntity handleAnyException(Exception ex) {
        log.error(ex.getMessage());
//        if(log.isTraceEnabled())
        ex.printStackTrace();
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex){
        loggingOfExceptions(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
        loggingOfExceptions(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @ExceptionHandler(UserAlreadyExistsException.class)
    public ResponseEntity handleUserAlreadyExistsException(UserAlreadyExistsException ex){
        loggingOfExceptions(ex);
        return ResponseEntity.status(HttpStatus.CONFLICT).build();
    }

    @ExceptionHandler(InsufficientBalanceException.class)
    public ResponseEntity handleInsufficientBalanceException(InsufficientBalanceException ex){
        loggingOfExceptions(ex);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    private void loggingOfExceptions(Throwable ex){
        if(log.isTraceEnabled())
            ex.printStackTrace();
        else if(log.isDebugEnabled())
            log.debug(ex.getMessage());
    }
}
