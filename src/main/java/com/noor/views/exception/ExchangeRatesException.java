package com.noor.views.exception;

import static com.noor.views.exception.FunctionalErrorCode.EXCHANGE_RATES_CANNOT_BE_RETRIEVED;
public class ExchangeRatesException extends FunctionalException {

    private static final long serialVersionUID = 1L;

    public ExchangeRatesException(String value) {
        super(EXCHANGE_RATES_CANNOT_BE_RETRIEVED, value);
    }

}
