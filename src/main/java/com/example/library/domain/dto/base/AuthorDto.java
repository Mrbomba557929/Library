package com.example.library.domain.dto.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record AuthorDto(String fio) {

    @Builder
    @JsonCreator
    public AuthorDto(@JsonProperty("fio") String fio) {
        this.fio = fio;
    }
}
