package com.example.library.factory;

import com.example.library.domain.model.RefreshToken;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class RefreshTokenFactory {

    private final UserFactory userFactory;
    private final Random random;

    public RefreshTokenFactory() {
        userFactory = new UserFactory();
        random = new Random();
    }

    public List<RefreshToken> giveAGivenNumberOfRefreshTokens(int numberOfRefreshTokens) {
        return Stream.generate(
                () -> RefreshToken.builder()
                        .token(UUID.randomUUID().toString())
                        .expiryDate(getRandomDate())
                        .build()
                )
                .limit(numberOfRefreshTokens)
                .toList();
    }

    public List<RefreshToken> giveAGivenNumberOfRefreshTokensWithUser(int numberOfRefreshTokens) {
        return Stream.generate(
                        () -> RefreshToken.builder()
                                .token(UUID.randomUUID().toString())
                                .expiryDate(getRandomDate())
                                .user(userFactory.giveAGivenNumberOfUsers(1).get(0))
                                .build()
                )
                .limit(numberOfRefreshTokens)
                .toList();
    }

    private Instant getRandomDate() {
        return new Date(Math.abs(System.currentTimeMillis() - random.nextLong(0, 100000))).toInstant();
    }
}
