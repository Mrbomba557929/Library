package com.example.library.domain.filter;

import com.example.library.domain.model.Author;
import com.example.library.domain.model.Book;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.lang.String.format;

public class Filter {

    public static <T extends Book> List<T> filterBy(FilterParameters filterParameters, List<T> target) {
        return target.stream().filter(book -> {

            for (Author author : book.getAuthors()) {
                if (filterParameters.getAuthors().contains(format("%s %s", author.getFirstName(), author.getLastName()))) {
                    return true;
                }
            }

            if (Objects.nonNull(filterParameters.getGenres()) && !filterParameters.getGenres().isEmpty()) {
                return  filterParameters.getGenres().contains(book.getGenre().getGenre());
            }

            return book.getCreatedAt().isAfter(filterParameters.getFrom()) &&
                   book.getCreatedAt().isBefore(filterParameters.getTo());

        }).collect(Collectors.toList());
    }
}
