package com.example.library.mapper;

import com.example.library.domain.model.Genre;
import com.example.library.domain.dto.base.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreMapper {

    public GenreDto toDto(Genre genre) {
        return GenreDto.builder()
                .genre(genre.getGenre())
                .build();
    }

    public Genre toEntity(GenreDto genreDto) {
        return Genre.builder()
                .genre(genreDto.genre())
                .build();
    }
}
