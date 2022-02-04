package com.example.library.factory;

import com.example.library.domain.model.Book;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

public class BookFactory {

    public static BookGenerator generator(int numberOfBooks) {
        return new BookGenerator(numberOfBooks);
    }

    public static class BookGenerator {

        private final Random random;
        private final int numberOfBooks;

        private String name;
        private LocalDate createdDate;
        private LocalDate addedDate;

        public BookGenerator(int numberOfBooks) {
            this.numberOfBooks = numberOfBooks;
            this.random = new Random();
        }

        public BookGenerator name(String name) {
            this.name = name;
            return this;
        }

        public BookGenerator createdDate(LocalDate createdDate) {
            this.createdDate = createdDate;
            return this;
        }

        public BookGenerator addedDate(LocalDate addedDate) {
            this.addedDate = addedDate;
            return this;
        }

        public List<Book> generate() {
            return Stream.generate(
                    () -> Book.builder()
                            .addedAt(Objects.requireNonNullElse(addedDate, getRandomDate()))
                            .creationAt(Objects.requireNonNullElse(createdDate, getRandomDate()))
                            .name(Objects.requireNonNullElse(name, UUID.randomUUID().toString()))
                            .build()
                    )
                    .limit(numberOfBooks)
                    .toList();
        }

        public List<Book> generateWithAuthors(AuthorFactory.AuthorGenerator authorGenerator) {
            return Stream.generate(
                    () -> Book.builder()
                            .addedAt(Objects.requireNonNullElse(addedDate, getRandomDate()))
                            .creationAt(Objects.requireNonNullElse(createdDate, getRandomDate()))
                            .name(Objects.requireNonNullElse(name, UUID.randomUUID().toString()))
                            .authors(authorGenerator.generate())
                            .build()
                    )
                    .limit(numberOfBooks)
                    .toList();
        }

        private LocalDate getRandomDate() {
            return LocalDateTime.ofInstant(
                    Instant.now().plusSeconds(random.nextLong(3600L, 31104000L)),
                    ZoneOffset.UTC
            ).toLocalDate();
        }
    }
}
