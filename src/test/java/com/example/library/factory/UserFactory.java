package com.example.library.factory;

import com.example.library.domain.model.User;

import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class UserFactory {

    private final Random random;

    public UserFactory() {
        random = new Random();
    }

    public List<User> giveAGivenNumberOfUsers(int numberOfUsers) {
        return Stream.generate(
                () -> User.builder()
                        .id(random.nextLong(0, 10000))
                        .email(UUID.randomUUID().toString())
                        .password(UUID.randomUUID().toString())
                        .build()
                )
                .limit(numberOfUsers)
                .toList();
    }
}
