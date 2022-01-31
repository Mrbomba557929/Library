package com.example.library.factory;

import com.example.library.domain.model.Author;
import com.example.library.domain.model.Book;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

@Component
public class BookFactory {

    private final Random random;

    public BookFactory() {
        random = new Random();
    }

    public List<Book> giveAGivenNumberOfBooks(int numberOfBooks) {
        return Stream.generate(
                () -> Book.builder()
                        .addedAt(getRandomDate())
                        .creationAt(getRandomDate())
                        .id(random.nextLong())
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
                        .authors(
                                Stream.generate(() -> Author.builder()
                                                .id(random.nextLong())
                                                .fio(UUID.randomUUID().toString())
                                                .build())
                                        .limit(numberOfAuthorsInEachBook)
                                        .toList()
                        )
                        .build()
                )
                .limit(numberOfBooks)
                .toList();
    }

    private LocalDate getRandomDate() {
        return LocalDateTime.ofInstant(
                new Date(Math.abs(System.currentTimeMillis() - random.nextLong())).toInstant(),
                ZoneOffset.UTC
        ).toLocalDate();
    }
}
