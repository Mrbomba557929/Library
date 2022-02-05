package com.example.library.service;

import com.example.library.domain.model.Genre;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.factory.GenreFactory;
import com.example.library.repository.GenreRepository;
import com.example.library.service.impl.GenreServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class GenreServiceImplTest {

    @Mock private GenreRepository genreRepository;
    private GenreService genreService;

    @BeforeEach
    void setUp() {
        genreService = new GenreServiceImpl(genreRepository);
    }

    @DisplayName("Test should properly find all the genres")
    @Test
    void shouldProperlyFindAll() {
        //given
        List<Genre> genres = GenreFactory.generator(10).generate();
        Mockito.when(genreRepository.findAll()).thenReturn(genres);

        //when
        List<Genre> expected = genreService.findAll();

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(genres.size());
    }

    @DisplayName("Test should properly save without errors")
    @Test
    void shouldProperlySaveWithoutErrors() {
        //given
        Genre genre = GenreFactory.generator(1).generate().get(0);
        Mockito.when(genreRepository.save(genre.getGenre())).thenReturn(genre);
        ArgumentCaptor<String> captor = ArgumentCaptor.forClass(String.class);

        //when
        Genre savedGenre = genreService.save(genre);

        //then
        verify(genreRepository).save(captor.capture());

        String captorValue = captor.getValue();

        assertThat(savedGenre).isNotNull();
        assertThat(captorValue).isEqualTo(genre.getGenre());
        assertThat(savedGenre.getGenre()).isEqualTo(genre.getGenre());
    }

    @DisplayName("Test should fail when save the genre")
    @Test
    void shouldFailWhenSaveTheGenre() {
        //given
        Genre genre = GenreFactory.generator(1).generate().get(0);
        Mockito.doThrow(FailedToSaveException.class).when(genreRepository).save(genre.getGenre());

        //then
        assertThatThrownBy(() -> genreService.save(genre)).isInstanceOf(FailedToSaveException.class);
    }
}