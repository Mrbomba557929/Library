package com.example.library.repository;

import com.example.library.domain.model.Book;
import com.example.library.factory.BookFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookStatsRepositoryTest extends AbstractRepositoryTest {

    private final BookStatsRepository bookStatsRepository;
    private final BookRepository bookRepository;

    @Autowired
    public BookStatsRepositoryTest(BookStatsRepository bookStatsRepository, BookRepository bookRepository) {
        this.bookStatsRepository = bookStatsRepository;
        this.bookRepository = bookRepository;
    }

    @DisplayName("Test should properly add an entry to the table for today's date")
    @Test
    void itShouldProperlyAddAnEntryToTheTableForTodayDate() {
        //when
        bookStatsRepository.increaseCounter();
        long countOfRecords = bookStatsRepository.count();

        //then
        assertThat(countOfRecords).isEqualTo(1);
    }

    @Sql(scripts = "/sql/book_stats_test.sql")
    @DisplayName("Test should properly increase counter")
    @Test
    void shouldProperlyIncreaseCounter() {
        //when
        bookStatsRepository.increaseCounter(); // it will increase already added counter for today
        long count = bookStatsRepository.getCountByDate(Instant.now());
        long countOfRecords = bookStatsRepository.count();

        //then
        assertThat(countOfRecords).isEqualTo(5);
        assertThat(count).isEqualTo(6);
    }

    @DisplayName("Test should properly return number of added books for today")
    @Test
    void shouldProperlyReturnNumberOfAddedBooksForToday() {
        //given
        List<Book> books = BookFactory.generator(15)
                .addedDate(LocalDate.now())
                .generate();
        bookRepository.saveAll(books);

        //when
        long count = bookStatsRepository.getNumberOfAddedBooksForToday();

        //then
        assertThat(count).isEqualTo(15);
    }

    @DisplayName("Test should properly return number of added books for week")
    @Test
    void shouldProperlyReturnNumberOfAddedBooksForWeek() {
        //given
        List<Book> books = BookFactory.generator(12)
                .addedDate(LocalDate.now())
                .generate();
        bookRepository.saveAll(books);

        //when
        long countForWeek = bookStatsRepository.getNumberOfAddedBooksForWeek();

        //then
        assertThat(countForWeek).isEqualTo(12);
    }

    @DisplayName("Test should properly return number of added books for month")
    @Test
    void shouldProperlyReturnNumberOfAddedBooksForMonth() {
        //given
        List<Book> books = BookFactory.generator(22)
                .addedDate(LocalDate.now())
                .generate();
        bookRepository.saveAll(books);

        //when
        long countForMonth = bookStatsRepository.getNumberOfAddedBooksForMonth();

        //then
        assertThat(countForMonth).isEqualTo(22);
    }

    @DisplayName("Test should properly return number of added books for year")
    @Test
    void shouldProperlyReturnNumberOfAddedBooksForYear() {
        //given
        List<Book> books = BookFactory.generator(32)
                .addedDate(LocalDate.now())
                .generate();
        bookRepository.saveAll(books);

        //when
        long countForYear = bookStatsRepository.getNumberOfAddedBooksForYear();

        //then
        assertThat(countForYear).isEqualTo(32);
    }

    @Sql(scripts = "/sql/book_stats_test.sql")
    @DisplayName("Test should return number of searches the books for today")
    @Test
    void itShouldProperlyReturnNumberOfSearchesBooksForToday() {
        //when
        long numberOfSearchesBooks = bookStatsRepository.getNumberOfSearchesBooksForToday();

        //then
        assertThat(numberOfSearchesBooks).isEqualTo(5);
    }

    @Sql(scripts = "/sql/book_stats_test.sql")
    @DisplayName("Test should return number of searches the books for month")
    @Test
    void isShouldProperlyReturnNumberOfSearchesBooksForMonth() {
        //when
        long numberOfSearchesBooks = bookStatsRepository.getNumberOfSearchesBooksForMonth();

        //then
        assertThat(numberOfSearchesBooks).isEqualTo(86);
    }

    @Sql(scripts = "/sql/book_stats_test.sql")
    @DisplayName("Test should return number of searches the books for year")
    @Test
    void isShouldProperlyReturnNumberOfSearchesBooksForYear() {
        //when
        long numberOfSearchesBooks = bookStatsRepository.getNumberOfSearchesBooksForYear();

        //then
        assertThat(numberOfSearchesBooks).isEqualTo(86);
    }

    @Sql(scripts = "/sql/book_stats_test.sql")
    @DisplayName("Test should return number of searches the books for all time")
    @Test
    void getNumberOfSearchesBooksForAllTime() {
        //when
        long numberOfSearchesBooks = bookStatsRepository.getNumberOfSearchesBooksForAllTime();

        //then
        assertThat(numberOfSearchesBooks).isEqualTo(86);
    }

    @Test
    void itShouldReturnCountAllBooks() {
        //given
        List<Book> books = BookFactory.generator(10).generate();
        bookRepository.saveAll(books);

        //when
        long countAllBooks = bookStatsRepository.getCountAllBooks();

        //then
        assertThat(countAllBooks).isEqualTo(books.size());
    }
}