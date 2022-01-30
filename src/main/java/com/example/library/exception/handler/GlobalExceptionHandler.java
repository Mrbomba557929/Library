package com.example.library.exception.handler;

import com.example.library.exception.business.ApplicationException;
import com.example.library.mapper.ErrorMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.util.List;

@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    private final ErrorMapper errorMapper;

    @ResponseBody
    @ExceptionHandler({ApplicationException.class})
    public ResponseEntity<?> handleNotSupportedTypeException(ApplicationException e) {
        return new ResponseEntity<>(errorMapper.toDto(e.getStatus(), e.getMessage(), e.getLink()), e.getStatus());
    }

    @ResponseBody
    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<?> handleWebExchangeBindException(WebExchangeBindException e) {
        List<String> errors = e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return new ResponseEntity<>(errorMapper.toDto(e.getStatus(), errors, null), e.getStatus());
    }
}
