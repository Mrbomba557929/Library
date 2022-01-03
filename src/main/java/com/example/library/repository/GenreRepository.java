package com.example.library.repository;

import com.example.library.domain.model.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {

    @Query(value = "SELECT * FROM genres WHERE genres.genre = ?1", nativeQuery = true)
    Optional<Genre> findByGenre(String genre);
}
