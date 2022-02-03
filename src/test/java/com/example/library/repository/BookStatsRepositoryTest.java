package com.example.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import java.time.Instant;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BookStatsRepositoryTest extends AbstractRepositoryTest {

    private final BookStatsRepository bookStatsRepository;

    @Autowired
    public BookStatsRepositoryTest(BookStatsRepository bookStatsRepository) {
        this.bookStatsRepository = bookStatsRepository;
    }

    @DisplayName("Test should properly add an entry to the table for today's date")
    @Test
    void shouldProperlyAddAnEntryToTheTableForTodayDate() {
        //when
        bookStatsRepository.increaseCounter();
        long countOfRecords = bookStatsRepository.count();

        //then
        assertThat(countOfRecords).isEqualTo(1);
    }

    @DisplayName("Test should properly increase counter")
    @Test
    void shouldProperlyIncreaseCounter() {
        //given
        bookStatsRepository.increaseCounter(); // it will add an entry to the table. Counter = 1

        //when
        bookStatsRepository.increaseCounter(); // it will increase already added counter for today
        long count = bookStatsRepository.getCountByDate(Instant.now());
        long countOfRecords = bookStatsRepository.count();

        //then
        assertThat(countOfRecords).isEqualTo(1);
        assertThat(count).isEqualTo(2);
    }

    @DisplayName("Test should properly return number of added books for today")
    @Test
    void shouldProperlyReturnNumberOfAddedBooksForToday() {
        //given
        bookStatsRepository.increaseCounter(); // it will add an entry to the table. Counter = 1

        //when
        bookStatsRepository.increaseCounter();
        bookStatsRepository.increaseCounter();

        long count = bookStatsRepository.getCountByDate(Instant.now());
        long countOfRecords = bookStatsRepository.countOfRecord();

        //then
        assertThat(countOfRecords).isEqualTo(1);
        assertThat(count).isEqualTo(3);
    }

    @Sql(scripts = "/sql/book_stats_test_6.sql")
    @DisplayName("Test should properly return number of added books for week")
    @Test
    void shouldProperlyReturnNumberOfAddedBooksForWeek() {
        //when
        long countForWeek = bookStatsRepository.getNumberOfAddedBooksForWeek();

        //then
        assertThat(countForWeek).isEqualTo(21);
    }

    @Test
    void getNumberOfAddedBooksForMonth() {
    }

    @Test
    void getNumberOfAddedBooksForYear() {
    }

    @Test
    void getNumberOfSearchesBooksForToday() {
    }

    @Test
    void getNumberOfSearchesBooksForMonth() {
    }

    @Test
    void getNumberOfSearchesBooksForYear() {
    }

    @Test
    void getNumberOfSearchesBooksForAllTime() {
    }

    @Test
    void getCountAllBooks() {

    }
}