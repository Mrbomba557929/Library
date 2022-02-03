package com.example.library.repository;

import com.example.library.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookStatsRepository extends JpaRepository<Book, Long> {

    @Transactional
    @Modifying
    @Query(value = """
              INSERT INTO book_search_stats (count, date)
              VALUES (1, now())
              ON CONFLICT (date)
              DO UPDATE SET count = count + 1
            """, nativeQuery = true)
    void increaseCounter();

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
            SELECT book_search_stats.count
            FROM book_search_stats
            WHERE book_search_stats.date = CURRENT_DATE
            """, nativeQuery = true)
    long getNumberOfSearchesBooksForToday();

    @Query(value = """
            SELECT SUM(book_search_stats.count)
            FROM book_search_stats
            WHERE EXTRACT(MONTH FROM book_search_stats.date) = EXTRACT(MONTH FROM CURRENT_DATE)
            """, nativeQuery = true)
    long getNumberOfSearchesBooksForMonth();

    @Query(value = """
            SELECT SUM(book_search_stats.count)
            FROM book_search_stats
            WHERE EXTRACT(YEAR FROM book_search_stats.date) = EXTRACT(YEAR FROM CURRENT_DATE)
            """, nativeQuery = true)
    long getNumberOfSearchesBooksForYear();

    @Query(value = """
            SELECT SUM(book_search_stats.count)
            FROM book_search_stats
            """, nativeQuery = true)
    long getNumberOfSearchesBooksForAllTime();

    @Query(value = """
            SELECT COUNT(*)
            FROM books""", nativeQuery = true)
    long getCountAllBooks();
}
