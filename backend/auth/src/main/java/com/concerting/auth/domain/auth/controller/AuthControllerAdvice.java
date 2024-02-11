package com.concerting.auth.domain.auth.controller;

import com.concerting.auth.domain.auth.exception.DatabaseException;
import com.concerting.auth.domain.auth.exception.PasswordNotMatchException;
import com.concerting.auth.domain.auth.exception.UserNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AuthControllerAdvice {
    @ExceptionHandler
    public ResponseEntity<String> handleUserNotFoundException(UserNotFoundException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleDatabaseException(DatabaseException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    @ExceptionHandler
    public ResponseEntity<String> handleException(PasswordNotMatchException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
