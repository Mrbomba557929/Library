package com.example.library.factory;

import com.example.library.domain.model.Book;
import com.example.library.exception.business.SortingException;
import com.example.library.exception.factory.ErrorFactory;
import com.google.common.base.Strings;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Stream;

import static com.example.library.exception.factory.ErrorMessage.SORTING_EXCEPTION;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

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

        public List<Book> generateWithAuthorsAndSortByFirstElementOfAuthors(AuthorFactory.AuthorGenerator authorGenerator, Sort.Direction direction) {
            return sortByFirstElementOfCollection(generateWithAuthors(authorGenerator), direction, "authors", "fio");
        }

        private LocalDate getRandomDate() {
            return LocalDateTime.ofInstant(
                    Instant.now().plusSeconds(random.nextLong(86400L, 31104000L)),
                    ZoneOffset.UTC
            ).toLocalDate();
        }

        private List<Book> sortByFirstElementOfCollection(List<Book> objects, Sort.Direction direction, String collection, String fieldOfElement) {

            if (Strings.isNullOrEmpty(collection) || Strings.isNullOrEmpty(fieldOfElement)) {
                return objects;
            }

            int numericDirection = direction == Sort.Direction.ASC ? 1 : -1;

            return objects.stream().sorted((item1, item2) -> {
                try {

                    List<?> list1 = (List<?>) getValueField(item1, collection);
                    List<?> list2 = (List<?>) getValueField(item2, collection);

                    if (list1.isEmpty() && list2.isEmpty()) {
                        return 0;
                    }

                    if (list1.isEmpty() || list2.isEmpty()) {
                        return numericDirection * (list1.isEmpty() ? -1 : 1);
                    }

                    String firstName1 = (String) getValueField(list1.get(0), fieldOfElement);
                    String firstName2 = (String) getValueField(list2.get(0), fieldOfElement);

                    return numericDirection * firstName1.compareToIgnoreCase(firstName2);

                } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
                    throw ErrorFactory.exceptionBuilder(SORTING_EXCEPTION)
                            .status(EXPECTATION_FAILED)
                            .build(SortingException.class);
                }
            }).toList();
        }

        private Object getValueField(Object object, String field) throws NoSuchFieldException, IllegalAccessException {
            Field clazzField = object.getClass().getDeclaredField(field);
            clazzField.setAccessible(true);
            return clazzField.get(object);
        }
    }
}
