package com.example.library.mapper;

import com.example.library.domain.model.Book;
import com.example.library.domain.dto.BookDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class BookMapper {

    private final AuthorMapper authorMapper;
    private final GenreMapper genreMapper;
    private final UrlMapper urlMapper;

    public BookDto toDto(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .name(book.getName())
                .creationAt(book.getCreationAt())
                .authors(book.getAuthors().stream().map(authorMapper::toDto).collect(Collectors.toList()))
                .genre(genreMapper.toDto(book.getGenre()))
                .url(book.getUrl() == null ? null : urlMapper.toDto(book.getUrl()))
                .build();
    }

    public BookDto.BookCountResponse toBookCountResponse(long count) {
        return BookDto.BookCountResponse.builder()
                .count(count)
                .message("number of books")
                .build();
    }

    public Book toEntity(BookDto bookDto) {
        return Book.builder()
                .id(bookDto.id())
                .name(bookDto.name())
                .creationAt(bookDto.creationAt())
                .authors(bookDto.authors().stream().map(authorMapper::toEntity).collect(Collectors.toList()))
                .genre(genreMapper.toEntity(bookDto.genre()))
                .url(bookDto.url() == null ? null : urlMapper.toEntity(bookDto.url()))
                .build();
    }
}
