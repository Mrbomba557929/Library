package com.example.library.factory;

import com.example.library.domain.model.User;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class UserFactory {

    public static UserGenerator generator(int numberOfUsers) {
        return new UserGenerator(numberOfUsers);
    }

    public static class UserGenerator {

        private final int numberOfUsers;

        private String email;
        private String password;

        public UserGenerator(int numberOfUsers) {
            this.numberOfUsers = numberOfUsers;
        }

        public UserGenerator email(String email) {
            this.email = email;
            return this;
        }

        public UserGenerator password(String password) {
            this.password = password;
            return this;
        }

        public List<User> generate() {
            return Stream.generate(
                    () -> User.builder()
                            .email(Objects.requireNonNullElse(email, UUID.randomUUID().toString()))
                            .password(Objects.requireNonNullElse(password, UUID.randomUUID().toString()))
                            .build()
                    )
                    .limit(numberOfUsers)
                    .toList();
        }
    }
}
