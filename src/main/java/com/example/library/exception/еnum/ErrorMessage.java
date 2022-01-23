package com.example.library.exception.еnum;

import lombok.Getter;

@Getter
public enum ErrorMessage {

    NOT_FOUND_BOOK("Error: the specified book was not found!"),
    NOT_FOUND_GENRE("Error: the specified genre was not found!"),
    NOT_FOUND_USER_EXCEPTION("Error: the target user was not found!"),
    NOT_FOUND_AUTHORITY_EXCEPTION("Error: the target authority was not found!"),
    ILLEGAL_STATE_FILTER("Error: there was an error reading the filters!"),
    SEARCH_OPERATION_NOT_SUPPORTED("Error: unknown filtering operation!"),
    UNKNOWN_EXCEPTION_CLASS("Error: Errors occurred while creating 'exception'"),
    SORTING_EXCEPTION("Error: there were some problems while sorting entities!"),
    AUTHENTICATION_EXCEPTION("Error: password is invalid!"),
    REFRESH_TOKEN_NOT_FOUND("Error: refresh token was not found!"),
    TOKEN_EXPIRED("Error: token was expired!");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
