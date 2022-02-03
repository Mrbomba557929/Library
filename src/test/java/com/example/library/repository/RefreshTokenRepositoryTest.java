package com.example.library.repository;

import com.example.library.domain.model.RefreshToken;
import com.example.library.factory.RefreshTokenFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RefreshTokenRepositoryTest extends AbstractRepositoryTest {

    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final RefreshTokenFactory refreshTokenFactory;

    @Autowired
    public RefreshTokenRepositoryTest(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.refreshTokenFactory = new RefreshTokenFactory();
    }

    @DisplayName("Test should properly find the refresh token by the token")
    @Test
    void shouldProperlyFindRefreshTokenByToken() {
        //given
        RefreshToken refreshToken = refreshTokenFactory.giveAGivenNumberOfRefreshTokens(1).get(0);
        refreshTokenRepository.save(refreshToken);

        //when
        RefreshToken refreshTokenFromDb = refreshTokenRepository.findByToken(refreshToken.getToken()).orElse(null);

        //then
        assertThat(refreshTokenFromDb).isNotNull();
        assertThat(refreshTokenFromDb.getToken()).isEqualTo(refreshToken.getToken());
    }

    @DisplayName("Test should properly save the refresh token")
    @Test
    void shouldProperlySave() {
        //given
        RefreshToken refreshToken = refreshTokenFactory.giveAGivenNumberOfRefreshTokensWithUser(1).get(0);
        refreshToken.setUser(userRepository.save(refreshToken.getUser()));

        //when
        RefreshToken refreshTokenFromDb = refreshTokenRepository.save(
                refreshToken.getExpiryDate(),
                refreshToken.getToken(),
                refreshToken.getUser().getId()
        );

        //then
        assertThat(refreshTokenFromDb).isNotNull();
        assertThat(refreshTokenFromDb.getToken()).isEqualTo(refreshToken.getToken());
        assertThat(refreshTokenFromDb.getUser()).isNotNull();
        assertThat(refreshTokenFromDb.getUser().getId()).isEqualTo(refreshToken.getUser().getId());
    }

    @DisplayName("Test should properly delete the refresh token by the user id")
    @Test
    void shouldProperlyDeleteRefreshTokenByUserId() {
        //given
        RefreshToken refreshToken = refreshTokenFactory.giveAGivenNumberOfRefreshTokensWithUser(1).get(0);
        refreshToken.setUser(userRepository.save(refreshToken.getUser()));
        refreshTokenRepository.save(refreshToken);

        //when
        refreshTokenRepository.deleteByUserId(refreshToken.getUser().getId());
        RefreshToken shouldBeNull = refreshTokenRepository.findByToken(refreshToken.getToken()).orElse(null);

        //then
        assertThat(shouldBeNull).isNull();
    }
}