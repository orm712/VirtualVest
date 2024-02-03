package com.concerting.auth.domain.auth.exception;

public class DatabaseException extends RuntimeException{
    public DatabaseException(String message) {
        super(message);
    }
}
