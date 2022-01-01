package com.example.library.factory;

import com.example.library.domain.model.Book;
import com.example.library.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookFactory {

    private final AuthorFactory authorFactory;
    private final GenreFactory genreFactory;
    private final UrlFactory urlFactory;

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .name(book.getName())
                .createdAt(book.getCreatedAt())
                .authors(book.getAuthors().stream().map(authorFactory::toDto).collect(Collectors.toSet()))
                .genre(genreFactory.toDto(book.getGenre()))
                .url(urlFactory.toDto(book.getUrl()))
                .build();
    }
}
