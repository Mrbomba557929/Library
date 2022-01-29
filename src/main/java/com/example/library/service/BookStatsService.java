package com.example.library.service;

public interface BookStatsService {

    /**
     * Method that gives the number of added books for today.
     *
     * @return the number of added books for today.
     */
    long getNumberOfAddedBooksForToday();

    /**
     * Method that gives the number of added books for week.
     *
     * @return the number of added books for week.
     */
    long getNumberOfAddedBooksForWeek();

    /**
     * Method that gives the number of added books for month.
     *
     * @return the number of added books for month.
     */
    long getNumberOfAddedBooksForMonth();

    /**
     * Method that gives the number of added books for year.
     *
     * @return the number of added books for year.
     */
    long getNumberOfAddedBooksForYear();

    /**
     * The method returns the number of search queries for today.
     *
     * @return the number of search queries for today.
     */
    long getNumberOfSearchesBooksForToday();

    /**
     * The method returns the number of search queries for month.
     *
     * @return the number of search queries for month.
     */
    long getNumberOfSearchesBooksForMonth();

    /**
     * The method returns the number of search queries for year.
     *
     * @return the number of search queries for year.
     */
    long getNumberOfSearchesBooksForYear();

    /**
     * The method returns the number of search queries for all time.
     *
     * @return the number of search queries for all time.
     */
    long getNumberOfSearchesBooksForAllTime();

    /**
     * The method returns the number of all books.
     *
     * @return the number of all books.
     */
    long getNumberOfAllBooks();
}
