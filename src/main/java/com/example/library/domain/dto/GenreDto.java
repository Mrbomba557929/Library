package com.example.library.domain.dto;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record GenreDto(String genre) {

    @Builder
    @JsonCreator
    public GenreDto(@JsonProperty("genre") String genre) {
        this.genre = genre;
    }
}
