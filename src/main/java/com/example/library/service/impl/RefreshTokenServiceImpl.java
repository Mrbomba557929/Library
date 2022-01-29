package com.example.library.service.impl;

import com.example.library.domain.model.RefreshToken;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.exception.business.NotFound;
import com.example.library.exception.business.TokenExpiredException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.repository.RefreshTokenRepository;
import com.example.library.service.RefreshTokenService;
import com.example.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

import static com.example.library.exception.factory.ErrorMessage.REFRESH_TOKEN_NOT_FOUND;
import static com.example.library.exception.factory.ErrorMessage.TOKEN_EXPIRED;
import static org.springframework.http.HttpStatus.*;

@Service
@RequiredArgsConstructor
public class RefreshTokenServiceImpl implements RefreshTokenService {

    @Value("${library.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserService userService;

    @Override
    public RefreshToken findByToken(String token) {
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(() ->
                        ErrorFactory.exceptionBuilder(REFRESH_TOKEN_NOT_FOUND)
                                .status(NOT_FOUND)
                                .link("RefreshTokenServiceImpl/findByToken")
                                .build(NotFound.class));
    }

    @Override
    public RefreshToken createRefreshToken(Long id) {
        try {
            return refreshTokenRepository.save(
                    Instant.now().plusMillis(refreshTokenDurationMs),
                    UUID.randomUUID().toString(),
                    userService.findById(id).getId()
            );
        } catch (DataAccessException e) {
            throw ErrorFactory.exceptionBuilder(e.getMessage())
                    .status(EXPECTATION_FAILED)
                    .link("RefreshTokenServiceImpl/createRefreshToken")
                    .build(FailedToSaveException.class);
        }
    }

    @Override
    public RefreshToken verifyExpiration(String refreshTokenRequest) {
        RefreshToken refreshToken = findByToken(refreshTokenRequest);

        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw ErrorFactory.exceptionBuilder(TOKEN_EXPIRED)
                    .status(FORBIDDEN)
                    .link("RefreshTokenServiceImpl/verifyExpiration")
                    .build(TokenExpiredException.class);
        }

        return refreshToken;
    }

    @Override
    public void deleteByUserId(Long id) {
        refreshTokenRepository.deleteByUserId(id);
    }
}
