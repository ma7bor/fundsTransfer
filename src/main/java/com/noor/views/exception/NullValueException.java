package com.noor.views.exception;

import java.util.List;

import static java.lang.String.join;
import static com.noor.views.exception.FunctionalErrorCode.NOT_NULL_FIELD;
import static com.noor.views.exception.FunctionalErrorCode.NOT_NULL_FIELDS;

public class NullValueException extends FunctionalException {

    private static final long serialVersionUID = 4896893580503414663L;

    public NullValueException(String field) {
        super(NOT_NULL_FIELD, field);
    }

    public NullValueException(List<String> fields) {
        super(NOT_NULL_FIELDS, join(", ", fields));
    }


}
