package com.noor.views.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolationException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(FunctionalException.class)
    public ResponseEntity<RestError> handleFunctionalException(FunctionalException exception) {
        return ResponseEntity
                .status(exception.getErrorCode().getHttpStatus())
                .body(RestError.builder()
                        .code(exception.getErrorCode()
                                .getCode())
                        .message(exception.getMessage())
                        .build());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<RestError> handleEntityIllegalArgumentException(IllegalArgumentException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestError.builder()
                .code(String.valueOf(FunctionalErrorCode.BAD_REQUEST.getCode()))
                .message(exception.getMessage())
                .build());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<RestError> handleEntityConstraintViolationException(ConstraintViolationException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(RestError.builder()
                .code(String.valueOf(FunctionalErrorCode.BAD_REQUEST.getCode()))
                .message(exception.getMessage())
                .build());
    }
}
