package com.noor.views.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static java.lang.String.format;

/**
 * This enum represents the functional error codes.
 */
@Getter
public enum FunctionalErrorCode {

    BAD_REQUEST(0, HttpStatus.BAD_REQUEST, "Bad Request"),
    EXCHANGE_RATES_CANNOT_BE_RETRIEVED(2, HttpStatus.BAD_REQUEST, "EXCHANGE_RATES_CANNOT_BE_RETRIEVED"),
    NOT_FOUND_ENTITY_ID(3, HttpStatus.NOT_FOUND, "No record of type %s and with id %s is present in the database"),
    NOT_FOUND_ENTITY_VALUE(4, HttpStatus.NOT_FOUND, "No record of type %s and with value : %s is present in the database"),
    NOT_FOUND_ENTITY(5, HttpStatus.NOT_FOUND, "No record of type %s found"),
    NOT_FOUND(6, HttpStatus.NOT_FOUND, "Not found"),
    NOT_FOUND_BY_VALUE(7, HttpStatus.NOT_FOUND, "No record of type %s and with value %s is present in the database"),
    NOT_NULL_ENTITY(8, HttpStatus.BAD_REQUEST, "Entity %s is required."),
    NOT_NULL_FIELD(9, HttpStatus.BAD_REQUEST, "Le champs suivant est obligatoire: %s"),
    JUST_MESSAGE(10, HttpStatus.BAD_REQUEST, "%s"),
    NOT_NULL_FIELDS(11, HttpStatus.BAD_REQUEST, "Les champs suivants sont obligatoires : %s"),
    MUST_BE_NULL_FIELD(12, HttpStatus.BAD_REQUEST, "The following fields must be null : %s"),
    NOT_BLANK_OR_EMPTY_FIELD(12, HttpStatus.BAD_REQUEST, "The following field is empty : %s"),
    INSUFFICIENT_FUNDS(13, HttpStatus.BAD_REQUEST, "Insufficient funds for making the transfer : %s"),
    ACCOUNT_NOT_FOUND(14,HttpStatus.BAD_REQUEST, "Account not found with Id : %s"),
    DEPOSIT_EXCEPTION(15,HttpStatus.BAD_REQUEST, "Deposit could be happened : %s");
    private final String code;
    private final HttpStatus httpStatus;
    private final String messageTemplate;

    FunctionalErrorCode(int code, HttpStatus httpStatus, String messageTemplate) {
        this.code = format("%03d", code);
        this.httpStatus = httpStatus;
        this.messageTemplate = messageTemplate;
    }
}
