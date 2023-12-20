package com.soyhenry.ExpensesApp.exception;

import org.springframework.http.HttpStatus;

public class DataValidationException extends RuntimeException {

    private final ErrorCode errorCode;

    public DataValidationException(final ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public ErrorCode getErrorCode() {
        return this.errorCode;
    }

}
