package com.example.library.repository;

import com.example.library.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Transactional
    @Modifying
    @Query(value = """
            INSERT INTO authors_books
            VALUES (?2, ?1)
            """, nativeQuery = true)
    void addBook(Long idBook, Long idAuthor);

    @Transactional
    @Query(value = """
            WITH e AS (
                INSERT INTO authors (fio) VALUES (?1)
                ON CONFLICT ON CONSTRAINT authors_fio_key DO NOTHING
                RETURNING id, fio
            )
                SELECT *
                FROM e
            UNION
                SELECT *
                FROM authors
                WHERE fio=?1
            """, nativeQuery = true)
    Author save(String fio);
}
