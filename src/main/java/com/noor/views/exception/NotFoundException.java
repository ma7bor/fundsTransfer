package com.noor.views.exception;

import static java.lang.String.valueOf;

public class NotFoundException extends FunctionalException {

    private static final long serialVersionUID = 1L;
    public NotFoundException(String value) {
        super(FunctionalErrorCode.ACCOUNT_NOT_FOUND, value);
    }
}
