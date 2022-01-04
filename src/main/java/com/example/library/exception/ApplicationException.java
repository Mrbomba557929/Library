package com.example.library.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ApplicationException extends RuntimeException {
    private HttpStatus status;
    private String link;
    private String message;
    private String developerMessage;
}
