package com.example.library.repository;

import com.example.library.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookStatsRepository extends JpaRepository<Book, Long> {

    @Query(value = """
            SELECT COUNT(*)
            FROM books
            WHERE EXTRACT(DAY FROM books.added_at) = EXTRACT(DAY FROM now())
            """, nativeQuery = true)
    long getNumberOfAddedBooksForToday();

    @Query(value = """
            SELECT COUNT(*)
            FROM books
            WHERE EXTRACT(WEEK FROM books.added_at) = EXTRACT(WEEK FROM now())
            """, nativeQuery = true)
    long getNumberOfAddedBooksForWeek();

    @Query(value = """
            SELECT COUNT(*)
            FROM books
            WHERE EXTRACT(MONTH FROM books.added_at) = EXTRACT(MONTH FROM now())
            """, nativeQuery = true)
    long getNumberOfAddedBooksForMonth();

    @Query(value = """
            SELECT COUNT(*)
            FROM books
            WHERE EXTRACT(YEAR FROM books.added_at) = EXTRACT(YEAR FROM now())
            """, nativeQuery = true)
    long getNumberOfAddedBooksForYear();

    @Query(value = """
            SELECT stats.count
            FROM stats
            WHERE stats.date = CURRENT_DATE
            """, nativeQuery = true)
    long getNumberOfSearchesBooksForToday();

    @Query(value = """
            SELECT SUM(stats.count)
            FROM stats
            WHERE EXTRACT(MONTH FROM stats.date) = EXTRACT(MONTH FROM CURRENT_DATE)
            """, nativeQuery = true)
    long getNumberOfSearchesBooksForMonth();

    @Query(value = """
            SELECT SUM(stats.count)
            FROM stats
            WHERE EXTRACT(YEAR FROM stats.date) = EXTRACT(YEAR FROM CURRENT_DATE)
            """, nativeQuery = true)
    long getNumberOfSearchesBooksForYear();

    @Query(value = """
            SELECT SUM(stats.count)
            FROM stats
            """, nativeQuery = true)
    long getNumberOfSearchesBooksForAllTime();

    @Query(value = """
            SELECT COUNT(*)
            FROM books""", nativeQuery = true)
    long getCountAllBooks();
}
