package com.example.library.repository;

import com.example.library.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = """
            SELECT *
            FROM authors a
            INNER JOIN authors_books ab on a.id = ab.author_id
            INNER JOIN books b on ab.book_id = b.id
            """, nativeQuery = true)
    List<Author> findAll();

    @Modifying
    @Query(value = """
            INSERT INTO authors_books
            VALUES (?2, ?1)
            """, nativeQuery = true)
    void addBook(Long idBook, Long idAuthor);

    @Modifying
    @Query(value = """
            WITH e AS (
                INSERT INTO authors VALUES (?1, ?2)
                ON CONFLICT("first_name", "last_name") DO NOTHING
                RETURNING *
            )
                SELECT * FROM e
            UNION
                SELECT first_name, last_name FROM authors WHERE first_name=?1 AND last_name=?2
            """, nativeQuery = true)
    Author save(String firstName, String lastName);
}
