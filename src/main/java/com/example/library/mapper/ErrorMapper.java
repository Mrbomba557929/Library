package com.example.library.mapper;

import com.example.library.domain.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class ErrorMapper {

    public ErrorDto toDto(HttpStatus status, String message, String link) {
        return ErrorDto.builder()
                .status(status)
                .message(message)
                .link(link)
                .timestamp(Instant.now())
                .build();
    }
}
