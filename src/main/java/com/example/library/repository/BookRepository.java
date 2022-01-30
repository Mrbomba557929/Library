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

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query(value = """
            SELECT new com.example.library.domain.dto.base.BookCreationDate(book.creationAt)
            FROM Book book""")
    List<BookCreationDate> getCreationDates();

    @Query(value = """
            WITH concate_fios AS
            (
            	SELECT books.id, string_agg(authors.fio, '') AS all_fios_authors
            	FROM books
            	INNER JOIN authors_books on authors_books.book_id = books.id
            	INNER JOIN authors on authors_books.author_id = authors.id
            	GROUP BY books.id
            )
            SELECT *
            FROM books
            INNER JOIN concate_fios ON concate_fios.id = books.id
            ORDER BY concate_fios.all_fios_authors, books.id ASC
            """,
            countQuery = """
            WITH concate_fios AS
            (
            	SELECT books.id, string_agg(authors.fio, '') AS all_fios_authors
            	FROM books
            	INNER JOIN authors_books on authors_books.book_id = books.id
            	INNER JOIN authors on authors_books.author_id = authors.id
            	GROUP BY books.id
            )
            SELECT COUNT(*)
            FROM books
            INNER JOIN concate_fios ON concate_fios.id = books.id
            GROUP BY books.name
            """, nativeQuery = true)
    Page<Book> findAllSortedByFirstElementFromAuthorsListASC(Pageable pageable);

    @Query(value = """
            WITH concate_fios AS
            (
            	SELECT books.id, string_agg(authors.fio, '') AS all_fios_authors
            	FROM books
            	INNER JOIN authors_books on authors_books.book_id = books.id
            	INNER JOIN authors on authors_books.author_id = authors.id
            	GROUP BY books.id
            )
            SELECT *
            FROM books
            INNER JOIN concate_fios ON concate_fios.id = books.id
            ORDER BY concate_fios.all_fios_authors, books.id DESC
            """,
            countQuery = """
            WITH concate_fios AS
            (
            	SELECT books.id, string_agg(authors.fio, '') AS all_fios_authors
            	FROM books
            	INNER JOIN authors_books on authors_books.book_id = books.id
            	INNER JOIN authors on authors_books.author_id = authors.id
            	GROUP BY books.id
            )
            SELECT COUNT(*)
            FROM books
            INNER JOIN concate_fios ON concate_fios.id = books.id
            GROUP BY books.name
            """, nativeQuery = true)
    Page<Book> findAllSortedByFirstElementFromAuthorsListDESC(Pageable pageable);
}