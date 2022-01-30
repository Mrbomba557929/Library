package com.example.library.mapper;

import com.example.library.domain.dto.ErrorDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;

@Component
public class ErrorMapper {

    public ErrorDto toDto(HttpStatus status, String message, String link) {
        return ErrorDto.builder()
                .status(status)
                .messages(List.of(message))
                .link(link)
                .timestamp(Instant.now())
                .build();
    }

    public ErrorDto toDto(HttpStatus status, List<String> messages, String link) {
        return ErrorDto.builder()
                .status(status)
                .messages(messages)
                .link(link)
                .timestamp(Instant.now())
                .build();
    }
}
