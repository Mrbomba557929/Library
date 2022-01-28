package com.example.library.controller;

import com.example.library.domain.dto.RefreshTokenDto;
import com.example.library.domain.dto.UserDto;
import com.example.library.domain.model.RefreshToken;
import com.example.library.domain.model.User;
import com.example.library.dtofactory.UserFactory;
import com.example.library.exception.AuthenticationException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.security.jwt.JwtUtils;
import com.example.library.service.RefreshTokenService;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.example.library.exception.factory.ErrorMessage.AUTHENTICATION_EXCEPTION;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private static final String REGISTRATION = "/registration";
    private static final String AUTHENTICATION = "/authentication";
    private static final String UPDATE_ACCESS_TOKEN = "/updateAccessToken";

    private final JwtUtils jwtUtils;
    private final UserFactory userFactory;
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;

    @PostMapping(REGISTRATION)
    public ResponseEntity<?> registration(@RequestBody @Valid UserDto.UserRegistrationRequestDto request) {
        User savedUser = userService.registration(userFactory.toEntity(request), passwordEncoder);
        return new ResponseEntity<>(userFactory.toDto(savedUser), HttpStatus.CREATED);
    }

    @PostMapping(AUTHENTICATION)
    public ResponseEntity<?> authentication(@RequestBody @Valid UserDto.UserAuthenticationRequestDto request) {
        User foundUser = userService.findByEmail(request.email());

        if (passwordEncoder.matches(request.password(), foundUser.getPassword())) {
            return new ResponseEntity<>(generateAccessResponse(foundUser), HttpStatus.OK);
        }

        throw ErrorFactory.exceptionBuilder(AUTHENTICATION_EXCEPTION)
                .status(UNAUTHORIZED)
                .build(AuthenticationException.class);
    }

    @PostMapping(UPDATE_ACCESS_TOKEN)
    public ResponseEntity<?> updateAccessToken(@RequestBody @Valid RefreshTokenDto.TokenRefreshRequestDto refreshTokenRequest) {

        RefreshToken refreshToken = refreshTokenService.verifyExpiration(refreshTokenRequest.refreshToken());
        UserDto userDto = userFactory.toDto(refreshToken.getUser());
        String token = jwtUtils.generateJwtToken(userDto.email());

        return new ResponseEntity<>(userFactory.toResponseDto(userDto, refreshToken.getToken(), token), HttpStatus.OK);
    }

    private UserDto.UserResponseDto generateAccessResponse(User user) {
        return userFactory.toResponseDto(
                userFactory.toDto(user),
                jwtUtils.generateJwtToken(user.getEmail()),
                refreshTokenService.createRefreshToken(user.getId()).getToken()
        );
    }
}
