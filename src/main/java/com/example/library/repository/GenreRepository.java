package com.example.library.repository;

import com.example.library.domain.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Override
    @Query(value = """
            SELECT genre
            FROM genres
            """, nativeQuery = true)
    List<Genre> findAll();

    @Query(value = """
            WITH e AS (
                INSERT INTO genres (genre) VALUES (?1)
                ON CONFLICT("genre") DO NOTHING
                RETURNING genre
            )
            SELECT genre FROM e
            UNION
            SELECT genre FROM genres WHERE genre=?1""", nativeQuery = true)
    Genre save(String genre);
}
