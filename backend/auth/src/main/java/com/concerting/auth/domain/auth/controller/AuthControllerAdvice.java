package com.concerting.auth.domain.auth.controller;

import com.concerting.auth.domain.auth.exception.DatabaseException;
import com.concerting.auth.domain.auth.exception.ErrorCode;
import com.concerting.auth.domain.auth.exception.PasswordNotMatchException;
import com.concerting.auth.domain.auth.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class AuthControllerAdvice {

    private ResponseEntity<?> buildResponseEntity(ErrorCode errorCode, String message){
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("status", errorCode.getStatus().value());
        body.put("error", errorCode.code());
        body.put("message", message != null ? message : errorCode.message());

        return new ResponseEntity<>(body, errorCode.getStatus());
    }

    @ExceptionHandler(UserNotFoundException.class)
    protected ResponseEntity<?> handleUserNotFoundException(UserNotFoundException e) {
        return buildResponseEntity(ErrorCode.USER_NOT_FOUND, e.getMessage());
    }

    @ExceptionHandler(DatabaseException.class)
    protected ResponseEntity<?> handleDatabaseException(DatabaseException e) {
        return buildResponseEntity(ErrorCode.DATABASE_ERROR, e.getMessage());
    }

    @ExceptionHandler(PasswordNotMatchException.class)
    protected ResponseEntity<?> handleException(PasswordNotMatchException e) {
        return buildResponseEntity(ErrorCode.DATABASE_ERROR, e.getMessage());
    }
}
