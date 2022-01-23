package com.example.library.domain.dto;

import com.example.library.domain.model.Authority;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.Set;

public record UserDto(Long id, String email, String password, Set<Authority> authorities) {

    @Builder
    @JsonCreator
    public UserDto(@JsonProperty("id") Long id, @JsonProperty("email") String email,
                   @JsonProperty("password") String password, @JsonProperty("authorities") Set<Authority> authorities) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
    }

    public static record UserRegistrationRequestDto(String email, String password, String confirmPassword) {

        @Builder
        @JsonCreator
        public UserRegistrationRequestDto(@JsonProperty("email") String email, @JsonProperty("password") String password,
                                   @JsonProperty("confirmPassword") String confirmPassword) {
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }
    }

    public static record UserAuthenticationRequestDto(String email, String password) {

        @Builder
        @JsonCreator
        public UserAuthenticationRequestDto(@JsonProperty("email") String email, @JsonProperty("password") String password) {
            this.email = email;
            this.password = password;
        }
    }

    public static record UserResponseDto(UserDto userDto, String accessToken, String refreshToken) {

        @Builder
        @JsonCreator
        public UserResponseDto(@JsonProperty("userDto") UserDto userDto, @JsonProperty("accessToken") String accessToken,
                               @JsonProperty("refreshToken") String refreshToken) {
            this.userDto = userDto;
            this.accessToken = accessToken;
            this.refreshToken = refreshToken;
        }
    }
}
