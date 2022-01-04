package com.example.library.exception.еnum;

import lombok.Getter;

@Getter
public enum ErrorMessage {
    NOT_FOUND_BOOK("Error: the specified book was not found!"),
    NOT_FOUND_GENRE("Error: the specified genre was not found!"),
    ILLEGAL_STATE_FILTER("Error: there was an error reading the filters!"),
    SEARCH_OPERATION_NOT_SUPPORTED("Error: unknown filtering operation!"),
    UNKNOWN_EXCEPTION_CLASS("Error: Errors occured while creating 'exception'");

    private final String message;

    ErrorMessage(String message) {
        this.message = message;
    }
}
