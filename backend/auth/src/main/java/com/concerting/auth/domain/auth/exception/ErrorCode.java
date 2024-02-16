package com.concerting.auth.domain.auth.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "User not found"),
    PASSWORD_NOT_MATCH(HttpStatus.BAD_REQUEST, "U002", "Password not match"),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "U003", "Database error");

    private final HttpStatus status;
    private final String code;
    private final String message;

    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }

    public HttpStatus getStatus(){
        return status;
    }

    public String code() {
        return code;
    }

    public String message() {
        return message;
    }
}
