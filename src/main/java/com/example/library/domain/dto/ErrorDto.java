package com.example.library.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;

public record ErrorDto(HttpStatus status, String message, String link, Instant timestamp) {

    @Builder
    @JsonCreator
    public ErrorDto(@JsonProperty("status") HttpStatus status, @JsonProperty("message") String message,
                    @JsonProperty("link") String link, @JsonProperty("timestamp") Instant timestamp) {
        this.status = status;
        this.message = message;
        this.link = link;
        this.timestamp = timestamp;
    }
}
