package com.example.library.dtofactory;

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
                .id(book.getId())
                .name(book.getName())
                .creationAt(book.getCreationAt())
                .authors(book.getAuthors().stream().map(authorFactory::toDto).collect(Collectors.toList()))
                .genre(genreFactory.toDto(book.getGenre()))
                .url(book.getUrl() == null ? null : urlFactory.toDto(book.getUrl()))
                .build();
    }

    public Book toEntity(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.id())
                .name(bookDto.name())
                .creationAt(bookDto.creationAt())
                .authors(bookDto.authors().stream().map(authorFactory::toEntity).collect(Collectors.toList()))
                .genre(genreFactory.toEntity(bookDto.genre()))
                .url(bookDto.url() == null ? null : urlFactory.toEntity(bookDto.url()))
                .build();
    }
}
