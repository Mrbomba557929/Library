package com.example.library.factory;

import com.example.library.domain.model.RefreshToken;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class RefreshTokenFactory {

    public static RefreshTokenGenerator generator(int numberOfRefreshTokens) {
        return new RefreshTokenGenerator(numberOfRefreshTokens);
    }

    public static class RefreshTokenGenerator {

        private final int numberOfRefreshTokens;
        private final Random random;

        private String token;
        private Instant expiryDate;

        public RefreshTokenGenerator(int numberOfRefreshTokens) {
            this.numberOfRefreshTokens = numberOfRefreshTokens;
            random = new Random();
        }

        public RefreshTokenGenerator token(String token) {
            this.token = token;
            return this;
        }

        public RefreshTokenGenerator expiryDate(Instant expiryDate) {
            this.expiryDate = expiryDate;
            return this;
        }

        public List<RefreshToken> generate() {
            return Stream.generate(
                    () -> RefreshToken.builder()
                            .token(Objects.requireNonNullElse(token, UUID.randomUUID().toString()))
                            .expiryDate(Objects.requireNonNullElse(expiryDate, getRandomDate()))
                            .build()
                    )
                    .limit(numberOfRefreshTokens)
                    .toList();
        }

        public List<RefreshToken> generateWithUsers(UserFactory.UserGenerator userGenerator) {
            return Stream.generate(
                    () -> RefreshToken.builder()
                            .token(Objects.requireNonNullElse(token, UUID.randomUUID().toString()))
                            .expiryDate(Objects.requireNonNullElse(expiryDate, getRandomDate()))
                            .user(userGenerator.generate().get(0))
                            .build()
                    )
                    .limit(numberOfRefreshTokens)
                    .toList();
        }

        private Instant getRandomDate() {
            return Instant.now().plusSeconds(random.nextLong(3600L, 31104000L));
        }
    }
}
