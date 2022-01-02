package com.example.library.exception;

public class NotFoundBookException extends RuntimeException {
    public NotFoundBookException(String message) {
        super(message);
    }
}
