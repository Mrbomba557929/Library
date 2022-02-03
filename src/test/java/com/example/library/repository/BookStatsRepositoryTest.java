package com.example.library.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BookStatsRepositoryTest extends AbstractRepositoryTest {

    private final BookStatsRepository bookStatsRepository;

    @Autowired
    public BookStatsRepositoryTest(BookStatsRepository bookStatsRepository) {
        this.bookStatsRepository = bookStatsRepository;
    }

    @DisplayName("Test should properly add an entry to the table for today's date")
    @Test
    void shouldProperlyAddAnEntryToTheTableForTodayDate() {
        //given



        //when


        //then
    }

    @Test
    void increaseCounter() {

    }

    @Test
    void getNumberOfAddedBooksForToday() {
    }

    @Test
    void getNumberOfAddedBooksForWeek() {
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