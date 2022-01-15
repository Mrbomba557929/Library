package com.example.library.repository;

import com.example.library.domain.dto.BookCreationDate;
import com.example.library.domain.model.Book;
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
            SELECT bk.id, bk.name, bk.creation_at, bk.added_at, bk.genre, bk.url_id
            FROM books bk
            INNER JOIN authors_books ab on bk.id = ab.book_id
            INNER JOIN authors a on a.id = ab.author_id
            JOIN genres g on bk.genre = g.genre
            WHERE bk.id = ?1
            """, nativeQuery = true)
    Optional<Book> findById(Long id);

    @Query(value = "SELECT COUNT(*) FROM books", nativeQuery = true)
    long getCountAllBooks();

    @Override
    @Query(value = """
            SELECT bk.id, bk.name, bk.creation_at, bk.added_at, bk.genre, bk.url_id
            FROM books bk
            INNER JOIN authors_books ab on bk.id = ab.book_id
            INNER JOIN authors a on a.id = ab.author_id
            JOIN genres g on bk.genre = g.genre
            """, nativeQuery = true)
    List<Book> findAll();

    @Query(value = """
            SELECT new com.example.library.domain.dto.BookCreationDate(book.creationAt)
            FROM Book book""")
    List<BookCreationDate> getCreationDates();
}