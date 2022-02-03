package com.example.library.repository;

import com.example.library.domain.model.Genre;
import com.example.library.factory.GenreFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class GenreRepositoryTest extends AbstractRepositoryTest {

    private final GenreRepository genreRepository;
    private final GenreFactory genreFactory;

    @Autowired
    public GenreRepositoryTest(GenreRepository genreRepository) {
        this.genreRepository = genreRepository;
        this.genreFactory = new GenreFactory();
    }

    @DisplayName(value = "Test should properly save the entity without conflicts")
    @Test
    void shouldProperlySaveWithoutConflicts() {
        //given
        Genre genre = genreFactory.createGenreWithCertainGenre("testGenre");

        //when
        Genre genreFromDb = genreRepository.save(genre.getGenre());

        //then
        assertThat(genreFromDb).isNotNull();
        assertThat(genreFromDb.getGenre()).isEqualTo(genre.getGenre());
    }

    @DisplayName(value = "Test should not save the entity, but return the existing one from database")
    @Test
    void shouldNotSaveButReturnTheExistingOne() {
        //given
        Genre genre = genreFactory.createGenreWithCertainGenre("testGenre");

        //when
        Genre genreFromDb = genreRepository.save(genre.getGenre());
        long countRecordsInDb = genreRepository.count();

        //then
        assertThat(genreFromDb).isNotNull();
        assertThat(genreFromDb.getGenre()).isEqualTo(genre.getGenre());
        assertThat(countRecordsInDb).isEqualTo(1L);
    }
}