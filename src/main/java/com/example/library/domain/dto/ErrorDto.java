package com.example.library.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.List;

public record ErrorDto(HttpStatus status, List<String> messages, String link, Instant timestamp) {

    @Builder
    @JsonCreator
    public ErrorDto(@JsonProperty("status") HttpStatus status, @JsonProperty("messages") List<String> messages,
                    @JsonProperty("link") String link, @JsonProperty("timestamp") Instant timestamp) {
        this.status = status;
        this.messages = messages;
        this.link = link;
        this.timestamp = timestamp;
    }
}
