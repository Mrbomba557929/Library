package com.example.library.mapper;

import com.example.library.domain.dto.UserDto;
import com.example.library.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toEntity(UserDto.UserRegistrationRequestDto userRegistrationRequestDto) {
        return User.builder()
                .email(userRegistrationRequestDto.email())
                .password(userRegistrationRequestDto.password())
                .build();
    }

    public UserDto toDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .authorities(user.getAuthorities())
                .build();
    }

    public UserDto.UserResponseDto toResponseDto(UserDto userDto, String accessToken, String refreshToken) {
        return UserDto.UserResponseDto.builder()
                .userDto(userDto)
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }
}
