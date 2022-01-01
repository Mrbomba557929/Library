package com.example.library.factory;

import com.example.library.domain.model.Genre;
import com.example.library.dto.GenreDto;
import org.springframework.stereotype.Component;

@Component
public class GenreFactory {

    public GenreDto toDto(Genre genre) {
        return GenreDto.builder()
                .genre(genre.getGenre())
                .build();
    }
}
