package com.example.library.repository;

import com.example.library.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @NonNull
    @Override
    @Query(value = """
            SELECT a.id, a.first_name, a.last_name
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

    @Query(value = """
            WITH e AS (
                INSERT INTO authors (first_name, last_name) VALUES (?1, ?2)
                ON CONFLICT ON CONSTRAINT authors_first_name_last_name_key DO NOTHING
                RETURNING id, first_name, last_name
            )
            SELECT id, first_name, last_name FROM e
            UNION
            SELECT id, first_name, last_name FROM authors WHERE first_name=?1 AND last_name=?2
            """, nativeQuery = true)
    Author save(String firstName, String lastName);
}
