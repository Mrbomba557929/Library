package com.example.library.exception.business;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = true)
@Data
public abstract class ApplicationException extends RuntimeException {
    protected HttpStatus status;
    protected String link;
    protected String message;
    protected Throwable cause;
}
