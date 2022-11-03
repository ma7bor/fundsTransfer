package com.noor.views.exception;

public class InsufficientFundsException extends FunctionalException {

    private static final long serialVersionUID = 1L;

    public InsufficientFundsException(String value) {
        super(FunctionalErrorCode.INSUFFICIENT_FUNDS, value);
    }
}