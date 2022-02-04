package com.example.library.repository;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.domain.model.Book;
import com.example.library.factory.BookFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BookRepositoryTest extends AbstractRepositoryTest{

    private final BookRepository bookRepository;

    @Autowired
    public BookRepositoryTest(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
}