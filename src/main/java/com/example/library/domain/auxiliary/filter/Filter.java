package com.example.library.domain.auxiliary.filter;

import com.example.library.domain.model.Author;
import com.example.library.domain.model.Book;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Filter {

    public static <T extends Book> List<T> filterBy(FilterParameters filterParameters, List<T> target) {
        return target.stream().filter(book ->
                checkListOfAuthors(book, filterParameters.getAuthors()) ||
                checkGenres(book, filterParameters.getGenres()) ||
                checkTimes(book, filterParameters.getFrom(), filterParameters.getTo())
        ).collect(Collectors.toList());
    }

    private static boolean checkListOfAuthors(Book book, List<String> FIOs) {

        for (Author author : book.getAuthors()) {
            if (FIOs.contains(format("%s %s", author.getFirstName(), author.getLastName()))) {
                return true;
            }
        }

        return false;
    }

    private static boolean checkGenres(Book book, List<String> genres) {
        return genres.contains(book.getGenre().getGenre());
    }

    private static boolean checkTimes(Book book, LocalDate from, LocalDate to) {

        if (Objects.nonNull(from) && Objects.nonNull(to)) {
            return book.getCreatedAt().isAfter(from) && book.getCreatedAt().isBefore(to);
        }

        return false;
    }
}
