package com.example.library.factory;

import com.example.library.domain.model.Book;
import com.example.library.domain.dto.BookDto;
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
                .authors(book.getAuthors().stream().map(authorFactory::toDto).collect(Collectors.toList()))
                .genre(genreFactory.toDto(book.getGenre()))
                .url(book.getUrl() == null ? null : urlFactory.toDto(book.getUrl()))
                .build();
    }

    public Book toEntity(BookDto bookDto) {
        return Book.builder()
                .name(bookDto.getName())
                .createdAt(bookDto.getCreatedAt())
                .authors(bookDto.getAuthors().stream().map(authorFactory::toEntity).collect(Collectors.toList()))
                .genre(genreFactory.toEntity(bookDto.getGenre()))
                .url(bookDto.getUrl() == null ? null : urlFactory.toEntity(bookDto.getUrl()))
                .build();
    }
}
