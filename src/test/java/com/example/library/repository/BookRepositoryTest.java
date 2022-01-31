package com.example.library.repository;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.domain.model.Book;
import com.example.library.factory.BookFactory;
import com.example.library.util.BookUtil;
import io.zonky.test.db.AutoConfigureEmbeddedDatabase;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseProvider.ZONKY;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.DatabaseType.POSTGRES;
import static io.zonky.test.db.AutoConfigureEmbeddedDatabase.RefreshMode.AFTER_EACH_TEST_METHOD;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

@DataJpaTest
@FlywayTest
@AutoConfigureEmbeddedDatabase(refresh = AFTER_EACH_TEST_METHOD, type = POSTGRES, provider = ZONKY)
@ExtendWith(MockitoExtension.class)
class BookRepositoryTest {

    private final BookRepository bookRepository;
    private final BookFactory bookFactory;
    private final BookUtil bookUtil;

    @Autowired
    public BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.bookFactory = new BookFactory();
        this.bookUtil = new BookUtil();
    }

    @AfterEach
    void tearDown() {
        bookRepository.deleteAll();
    }

    @DisplayName("Test should give all a creation dates of an all books.")
    @Test
    void shouldGiveAllCreationDatesOfAllBooks() {
        //given
        List<Book> books = bookFactory.giveAGivenNumberOfBooks(5);
        bookRepository.saveAll(books);

        //when
        List<BookCreationDate> expected = bookRepository.getCreationDates();

        //then
        assertThat(expected.size()).isEqualTo(books.size());

        boolean result = true;

        for (int i = 0; i < expected.size(); i++) {
            if (!expected.get(i).creationAt().isEqual(books.get(i).getCreationAt())) {
                result = false;
                break;
            }
        }

        assertThat(result).isTrue();
    }

    @DisplayName("Test should find all books sorted by first element from list of authors asc")
    @Test
    void shouldFindAllBooksSortedByFirstElementFromAuthorsListASC() {
        //given
        List<Book> books = bookFactory.giveAGivenNumberOfBooksWithAuthors(5, 1);
        List<Book> sortedBooks = bookUtil.sortByFirstElementOfCollection(books, ASC, "authors", "fio");
        bookRepository.saveAll(books);

        //when
        List<Book> expected = bookRepository
                .findAllSortedByFirstElementFromAuthorsListASC(PageRequest.ofSize(5))
                .getContent();

        //then
        assertThat(expected.size()).isEqualTo(sortedBooks.size());
        assertThat(expected.get(0).getName()).isEqualTo(sortedBooks.get(0).getName());
        assertThat(expected.get(1).getName()).isEqualTo(sortedBooks.get(1).getName());
        assertThat(expected.get(2).getName()).isEqualTo(sortedBooks.get(2).getName());
        assertThat(expected.get(3).getName()).isEqualTo(sortedBooks.get(3).getName());
        assertThat(expected.get(4).getName()).isEqualTo(sortedBooks.get(4).getName());
    }

    @DisplayName("Test should find all books sorted by first element from list of authors desc")
    @Test
    void shouldFindAllBooksSortedByFirstElementFromAuthorsListDESC() {
        //given
        List<Book> books = bookFactory.giveAGivenNumberOfBooksWithAuthors(5, 1);
        List<Book> sortedBooks = bookUtil.sortByFirstElementOfCollection(books, DESC, "authors", "fio");
        bookRepository.saveAll(books);

        //when
        List<Book> expected = bookRepository
                .findAllSortedByFirstElementFromAuthorsListDESC(PageRequest.ofSize(5))
                .getContent();

        //then
        assertThat(expected.size()).isEqualTo(sortedBooks.size());
        assertThat(expected.get(0).getName()).isEqualTo(sortedBooks.get(0).getName());
        assertThat(expected.get(1).getName()).isEqualTo(sortedBooks.get(1).getName());
        assertThat(expected.get(2).getName()).isEqualTo(sortedBooks.get(2).getName());
        assertThat(expected.get(3).getName()).isEqualTo(sortedBooks.get(3).getName());
        assertThat(expected.get(4).getName()).isEqualTo(sortedBooks.get(4).getName());
    }
}