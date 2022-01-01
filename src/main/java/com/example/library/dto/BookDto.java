package com.example.library.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {
    private String name;
    private Instant createdAt;
    private List<AuthorDto> authors;
    private GenreDto genre;
    private UrlDto url;
}
