package com.example.library.domain.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record UrlDto(String url) {

    @Builder
    @JsonCreator
    public UrlDto(@JsonProperty("url") String url) {
        this.url = url;
    }
}
