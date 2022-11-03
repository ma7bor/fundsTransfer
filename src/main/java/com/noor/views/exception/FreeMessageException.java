package com.noor.views.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FreeMessageException extends FunctionalException {
    private static final long serialVersionUID = 3515826689201951584L;

    public FreeMessageException(String message) {
        super(FunctionalErrorCode.JUST_MESSAGE,message);
    }
}
