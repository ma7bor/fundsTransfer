package com.noor.views.exception;

import static com.noor.views.exception.FunctionalErrorCode.DEPOSIT_EXCEPTION;

public class DepositException extends FunctionalException{

    private static final long serialVersionUID = 1L;

    public DepositException(String value) {
        super(DEPOSIT_EXCEPTION, value);
    }
}
