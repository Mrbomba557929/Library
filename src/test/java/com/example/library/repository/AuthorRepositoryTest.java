package com.example.library.repository;

import com.example.library.domain.model.Author;
import com.example.library.factory.AuthorFactory;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
@FlywayTest
@AutoConfigureEmbeddedDatabase(refresh = AFTER_EACH_TEST_METHOD, type = POSTGRES, provider = ZONKY)
@ExtendWith(MockitoExtension.class)
class AuthorRepositoryTest {

    private final AuthorRepository authorRepository;
    private final AuthorFactory authorFactory;

    @Autowired
    public AuthorRepositoryTest(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
        this.authorFactory = new AuthorFactory();
    }

    @DisplayName("Test should save an author without conflicts (there is no author in the database)")
    @Test
    void shouldSaveAnAuthorWithoutConflicts() {

        //given
        Author author = authorFactory.giveAGivenNumberOfAuthors(1).get(0);

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
        Author author = authorFactory.giveAGivenNumberOfAuthors(1).get(0);
        authorRepository.save(author);

        //when
        Author expected = authorRepository.save(author.getFio());

        //then
        assertThat(expected).isNotNull();
        assertThat(expected.getFio()).isEqualTo(author.getFio());
    }
}