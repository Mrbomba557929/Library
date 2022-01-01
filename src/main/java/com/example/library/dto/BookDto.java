package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDto {
    private String name;
    private Instant createdAt;
    private Set<AuthorDto> authors;
    private GenreDto genre;
    private UrlDto url;
}
