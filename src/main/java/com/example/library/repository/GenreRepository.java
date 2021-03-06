package com.example.library.repository;

import com.example.library.domain.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Transactional
    @Query(value = """
            WITH e AS (
                INSERT INTO genres (genre) VALUES (?1)
                ON CONFLICT ON CONSTRAINT genres_pkey DO NOTHING
                RETURNING genre
            )
                SELECT *
                FROM e
            UNION
                SELECT *
                FROM genres
                WHERE genre=?1""", nativeQuery = true)
    Genre save(String genre);
}
