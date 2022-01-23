package com.example.library.service;

import com.example.library.domain.model.RefreshToken;

public interface RefreshTokenService {

    /**
     * Method designed for finding the {@link RefreshToken} entity.
     *
     * @param token by which we will find the {@link RefreshToken} entity.
     * @return An {@link RefreshToken} entity found by provided the token.
     */
    RefreshToken findByToken(String token);

    /**
     * Method designed to create an {@link RefreshToken} entity based on provided user UUID.
     *
     * @param id by which we will create the {@link RefreshToken} entity.
     * @return An {@link RefreshToken} entity created based on provided user UUID.
     */
    RefreshToken createRefreshToken(Long id);

    /**
     * Method designed to verify the {@link RefreshToken} entity.
     *
     * @param refreshTokenRequest is a request, that contains a refresh token.
     * @return Verified an {@link RefreshToken} entity.
     */
    RefreshToken verifyExpiration(String refreshTokenRequest);

    /**
     * Method designed to delete the {@link RefreshToken} entity based on provided user UUID.
     *
     * @param id is an id, by which we will delete {@link RefreshToken} entity.
     */
    void deleteByUserUUID(Long id);
}
