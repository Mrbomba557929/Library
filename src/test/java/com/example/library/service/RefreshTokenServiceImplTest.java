package com.example.library.service;

import com.example.library.domain.model.RefreshToken;
import com.example.library.exception.business.NotFound;
import com.example.library.exception.business.TokenExpiredException;
import com.example.library.factory.RefreshTokenFactory;
import com.example.library.factory.UserFactory;
import com.example.library.repository.RefreshTokenRepository;
import com.example.library.service.impl.RefreshTokenServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class RefreshTokenServiceImplTest {

    @Mock private RefreshTokenRepository refreshTokenRepository;
    @Mock private UserService userService;
    private RefreshTokenService refreshTokenService;

    @BeforeEach
    void setUp() {
        refreshTokenService = new RefreshTokenServiceImpl(refreshTokenRepository, userService);
    }

    @DisplayName("Test should properly find the refresh token by the token")
    @Test
    void shouldProperlyFindByToken() {
        //given
        String token = UUID.randomUUID().toString();
        RefreshToken refreshToken = RefreshTokenFactory.generator(1).generate().get(0);
        Mockito.when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.of(refreshToken));

        //when
        RefreshToken expected = refreshTokenService.findByToken(token);

        //then
        assertThat(expected).isNotNull();
        verify(refreshTokenRepository, Mockito.times(1)).findByToken(token);
        assertThat(expected).isEqualTo(refreshToken);
    }

    @DisplayName("Test should fail when find the refresh token by the token")
    @Test
    void shouldFailWhenFindByToken() {
        //given
        String token = UUID.randomUUID().toString();
        Mockito.when(refreshTokenRepository.findByToken(token)).thenReturn(Optional.empty());

        //then
        assertThatThrownBy(() -> refreshTokenService.findByToken(token)).isInstanceOf(NotFound.class);
    }

    @DisplayName("Test should properly create the refresh token")
    @Test
    void shouldProperlyCreateRefreshToken() throws NoSuchFieldException, IllegalAccessException {
        //given
        Long id = 1L;
        RefreshToken refreshToken = RefreshTokenFactory.generator(1).generate().get(0);
        Mockito.when(refreshTokenRepository.save(Mockito.any(), Mockito.any(), Mockito.any())).thenReturn(refreshToken);
        Mockito.when(userService.findById(id)).thenReturn(UserFactory.generator(1).generate().get(0));

        Field refreshTokenDurationMs = refreshTokenService.getClass().getDeclaredField("refreshTokenDurationMs");
        refreshTokenDurationMs.setAccessible(true);
        refreshTokenDurationMs.set(refreshTokenService, 8000L);

        //when
        RefreshToken expected = refreshTokenService.createRefreshToken(id);

        //then
        assertThat(expected).isNotNull();
        verify(refreshTokenRepository, Mockito.times(1)).save(Mockito.any(), Mockito.any(), Mockito.any());
        assertThat(expected).isEqualTo(refreshToken);
    }

    @DisplayName("Test should properly verify expiration of the refresh token")
    @Test
    void shouldProperlyVerifyExpiration() {
        //given
        String refreshToken = UUID.randomUUID().toString();
        RefreshToken refreshTokenObject = RefreshTokenFactory.generator(1).generate().get(0);
        Mockito.when(refreshTokenRepository.findByToken(refreshToken)).thenReturn(Optional.of(refreshTokenObject));

        //when
        RefreshToken expected = refreshTokenService.verifyExpiration(refreshToken);

        //then
        assertThat(expected).isNotNull();
        assertThat(expected).isEqualTo(refreshTokenObject);
    }

    @DisplayName("Test should fail when verify expiration of the refresh token")
    @Test
    void shouldFailWhenVerifyExpiration() {
        //given
        String refreshToken = UUID.randomUUID().toString();
        RefreshToken refreshTokenObject = RefreshTokenFactory.generator(1).generate().get(0);
        refreshTokenObject.setExpiryDate(Instant.now().minusSeconds(300000L));
        Mockito.when(refreshTokenRepository.findByToken(refreshToken)).thenReturn(Optional.of(refreshTokenObject));

        //then
        assertThatThrownBy(() -> refreshTokenService.verifyExpiration(refreshToken)).isInstanceOf(TokenExpiredException.class);
        verify(refreshTokenRepository, Mockito.times(1)).delete(refreshTokenObject);
    }

    @DisplayName("Test should properly delete by the user id")
    @Test
    void shouldProperlyDeleteByUserId() {
        //given
        Long id = 1L;

        //when
        refreshTokenService.deleteByUserId(id);

        //then
        verify(refreshTokenRepository, Mockito.times(1)).deleteByUserId(id);
    }
}