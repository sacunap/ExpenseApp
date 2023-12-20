package com.soyhenry.ExpensesApp.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(DataValidationException.class)
    protected ResponseEntity<Object> handleDataValidationException(DataValidationException ex, WebRequest request) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErrorResponse errorResponse = new ErrorResponse(
                ex.getErrorCode().getCode(),
                ex.getErrorCode().getMessage(),
                status,
                ZonedDateTime.now()
        );

        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), status, request);
    }

    private static class ErrorResponse {
        private final String code;
        private final String message;
        private final HttpStatus httpStatus;
        private final ZonedDateTime timestamp;

        public ErrorResponse(String code, String message, HttpStatus httpStatus, ZonedDateTime timestamp) {
            this.code = code;
            this.message = message;
            this.httpStatus = httpStatus;
            this.timestamp = timestamp;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

        public HttpStatus getHttpStatus() {
            return httpStatus;
        }

        public ZonedDateTime getTimestamp() {
            return timestamp;
        }
    }
}
