package com.example.library.factory;

import com.example.library.domain.model.Author;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class AuthorFactory {

    public static AuthorGenerator generator(int numberOfAuthors) {
        return new AuthorGenerator(numberOfAuthors);
    }

    public static class AuthorGenerator {

        private final int numberOfAuthors;

        private String fio;

        public AuthorGenerator(int numberOfAuthors) {
            this.numberOfAuthors = numberOfAuthors;
        }

        public AuthorGenerator fio(String fio) {
            this.fio = fio;
            return this;
        }

        public List<Author> generate() {
            return Stream.generate(
                    () -> Author.builder()
                            .fio(Objects.requireNonNullElse(fio, UUID.randomUUID().toString()))
                            .build())
                    .limit(numberOfAuthors)
                    .toList();
        }
    }
}
