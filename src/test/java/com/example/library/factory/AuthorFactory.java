package com.example.library.factory;

import com.example.library.domain.model.Author;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

public class AuthorFactory {

    private final Random random;

    public AuthorFactory() {
        random = new Random();
    }

    public List<Author> giveAGivenNumberOfAuthors(int numberOfAuthors) {
        return Stream.generate(
                () -> Author.builder()
                        .id(random.nextLong(0, 100000))
                        .fio(UUID.randomUUID().toString())
                        .build())
                .limit(numberOfAuthors)
                .toList();
    }
}
