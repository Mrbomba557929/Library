package com.example.library.factory;

import com.example.library.domain.model.Genre;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

public class GenreFactory {

    public List<Genre> giveAGivenNumberOfGenres(int numberOfGenres) {
        return Stream.generate(
                () -> Genre.builder()
                        .genre(UUID.randomUUID().toString())
                        .build()
                )
                .toList();
    }

    public Genre createGenreWithCertainGenre(String genre) {
        return Genre.builder()
                .genre(genre)
                .build();
    }
}
