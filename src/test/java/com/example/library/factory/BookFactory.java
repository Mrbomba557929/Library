package com.example.library.factory;

import com.example.library.domain.model.Book;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

public class BookFactory {

    private final Random random;
    private final AuthorFactory authorFactory;

    public BookFactory() {
        random = new Random();
        authorFactory = new AuthorFactory();
    }

    public List<Book> giveAGivenNumberOfBooks(int numberOfBooks) {
        return Stream.generate(
                () -> Book.builder()
                        .addedAt(getRandomDate())
                        .creationAt(getRandomDate())
                        .id(random.nextLong(0, 100000))
                        .name(UUID.randomUUID().toString())
                        .build()
                )
                .limit(numberOfBooks)
                .toList();
    }

    public List<Book> giveAGivenNumberOfBooksWithAuthors(int numberOfBooks, int numberOfAuthorsInEachBook) {
        return Stream.generate(
                () -> Book.builder()
                        .addedAt(getRandomDate())
                        .creationAt(getRandomDate())
                        .id(random.nextLong())
                        .name(UUID.randomUUID().toString())
                        .authors(authorFactory.giveAGivenNumberOfAuthors(numberOfAuthorsInEachBook))
                        .build()
                )
                .limit(numberOfBooks)
                .toList();
    }

    private LocalDate getRandomDate() {
        return LocalDateTime.ofInstant(
                new Date(Math.abs(System.currentTimeMillis() - random.nextLong(0, 100000))).toInstant(),
                ZoneOffset.UTC
        ).toLocalDate();
    }
}
