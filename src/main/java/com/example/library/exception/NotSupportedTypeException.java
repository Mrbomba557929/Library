package com.example.library.exception;

public class NotSupportedTypeException extends RuntimeException {
    public NotSupportedTypeException(String message) {
        super(message);
    }
}
