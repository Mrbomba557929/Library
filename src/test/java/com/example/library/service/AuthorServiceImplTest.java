package com.example.library.service;

import com.example.library.domain.model.Author;
import com.example.library.factory.AuthorFactory;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.impl.AuthorServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ExtendWith(MockitoExtension.class)
class AuthorServiceImplTest {

    @Mock private AuthorRepository authorRepository;
    private AuthorService authorService;

    @BeforeEach
    void setUp() {
        authorService = new AuthorServiceImpl(authorRepository);
    }

    @DisplayName("Test should properly find all authors")
    @Test
    void shouldProperlyFindAllAuthors() {
        //given
        List<Author> authors = AuthorFactory.generator(10).generate();
        Mockito.when(authorRepository.findAll()).thenReturn(authors);

        //when
        List<Author> expected = authorService.findAll();

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.size()).isEqualTo(authors.size());
    }
}