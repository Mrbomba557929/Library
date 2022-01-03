package com.example.library.factory;

import com.example.library.domain.model.Genre;
import com.example.library.domain.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreFactory {

    public GenreDto toDto(Genre genre) {
        return GenreDto.builder()
                .genre(genre.getGenre())
                .build();
    }

    public Genre toEntity(GenreDto genreDto) {
        return Genre.builder()
                .genre(genreDto.getGenre())
                .build();
    }
}
