package com.example.library.repository;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.domain.model.Book;
import com.example.library.factory.AuthorFactory;
import com.example.library.factory.BookFactory;
import com.example.library.util.BookUtil;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

class BookRepositoryTest extends AbstractRepositoryTest{

    private final BookRepository bookRepository;
    private final BookUtil bookUtil;

    @Autowired
    public BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
        this.bookUtil = new BookUtil();
    }

    @DisplayName("Test should give all a creation dates of an all books.")
    @Test
    void shouldGiveAllCreationDatesOfAllBooks() {
        //given
        List<Book> books = BookFactory.generator(5).generate();
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
        List<Book> books = BookFactory.generator(5).generateWithAuthors(AuthorFactory.generator(1));
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
        List<Book> books = BookFactory.generator(5).generateWithAuthors(AuthorFactory.generator(1));
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