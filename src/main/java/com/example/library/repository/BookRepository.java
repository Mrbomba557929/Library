package com.example.library.repository;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.domain.model.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Override
    @Query(value = """
            SELECT
                bk.id, bk.name,
                bk.creation_at, bk.added_at,
                bk.genre, bk.url_id,
                a.id, a.fio
            FROM books bk
            INNER JOIN authors_books ab on bk.id = ab.book_id
            INNER JOIN authors a on a.id = ab.author_id
            JOIN genres g on bk.genre = g.genre
            WHERE bk.id = ?1
            """, nativeQuery = true)
    Optional<Book> findById(Long id);

    @Query(value = """
            WITH books_authors AS
            (
                SELECT
                    bk.id, bk.name,
                    bk.creation_at, bk.added_at,
                    bk.genre, bk.url_id,
                    a.id, a.fio,
                    ROW_NUMBER() OVER(PARTITION BY bk.name) AS number_row
                FROM books bk
                INNER JOIN authors_books ab on bk.id = ab.book_id
                INNER JOIN authors a on a.id = ab.author_id
            )
            SELECT *
            FROM books_authors
            WHERE books_authors.number_row = 1
            ORDER BY books_authors.fio ?1""",
           countQuery = """
            WITH books_authors AS
            (
                 SELECT
                     bk.id, bk.name,
                     bk.creation_at, bk.added_at,
                     bk.genre, bk.url_id,
                     a.id, a.fio,
                      ROW_NUMBER() OVER(PARTITION BY bk.name) AS number_row
                 FROM books bk
                 INNER JOIN authors_books ab on bk.id = ab.book_id
                 INNER JOIN authors a on a.id = ab.author_id
            )
            SELECT COUNT(*)
            FROM books_authors
            WHERE books_authors.number_row = 1
            """, nativeQuery = true)
    Page<Book> findAllSortedByFirstElementFromAuthorsList(String order, Pageable pageable);

    @Override
    @Query(value = """
            SELECT
                bk.id, bk.name,
                bk.creation_at, bk.added_at,
                bk.genre, bk.url_id,
                a.id, a.fio
            FROM books bk
            INNER JOIN authors_books ab on bk.id = ab.book_id
            INNER JOIN authors a on a.id = ab.author_id
            JOIN genres g on bk.genre = g.genre
            """, nativeQuery = true)
    List<Book> findAll();

    @Query(value = """
            SELECT new com.example.library.domain.dto.base.BookCreationDate(book.creationAt)
            FROM Book book""")
    List<BookCreationDate> getCreationDates();
}