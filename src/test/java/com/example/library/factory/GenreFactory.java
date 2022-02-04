package com.example.library.factory;

import com.example.library.domain.model.Genre;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Stream;

public class GenreFactory {

    public static GenreGenerator generator(int numberOfGenres) {
        return new GenreGenerator(numberOfGenres);
    }

    public static class GenreGenerator {

        private final int numberOfGenres;

        private String genre;

        public GenreGenerator(int numberOfGenres) {
            this.numberOfGenres = numberOfGenres;
        }

        public GenreGenerator genre(String genre) {
            this.genre = genre;
            return this;
        }

        public List<Genre> generate() {
            return Stream.generate(
                    () -> Genre.builder()
                            .genre(Objects.requireNonNullElse(genre, UUID.randomUUID().toString()))
                            .build()
                    )
                    .limit(numberOfGenres)
                    .toList();
        }
    }
}
