package com.example.library.exception.handler;

import com.example.library.exception.NotSupportedTypeException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler({NotSupportedTypeException.class})
    public ResponseEntity<?> handleNotSupportedTypeException(NotSupportedTypeException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
