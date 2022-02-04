package com.example.library.repository;

import com.example.library.domain.model.Author;
import com.example.library.factory.AuthorFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AuthorRepositoryTest extends AbstractRepositoryTest {

    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorRepositoryTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @DisplayName("Test should save an author without conflicts (there is no author in the database)")
    @Test
    void shouldSaveAnAuthorWithoutConflicts() {

        //given
        Author author = AuthorFactory.generator(1).generate().get(0);

        //when
        Author expected = authorRepository.save(author.getFio());

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getFio()).isEqualTo(author.getFio());
    }

    @DisplayName("Test should save an author with conflicts (there is author in the database)")
    @Test
    void shouldSaveAnAuthorWithConflicts() {

        //given
        Author author = AuthorFactory.generator(1).generate().get(0);
        authorRepository.save(author);

        //when
        Author expected = authorRepository.save(author.getFio());

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getFio()).isEqualTo(author.getFio());
    }
}