package com.example.library.domain.dto.base;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record RefreshTokenDto() {

    public static record TokenRefreshRequestDto(String refreshToken) {

        @Builder
        @JsonCreator
        public TokenRefreshRequestDto(@JsonProperty("refreshToken") String refreshToken) {
            this.refreshToken = refreshToken;
        }
    }
}
