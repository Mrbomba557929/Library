package com.example.library.repository;

import com.example.library.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

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
