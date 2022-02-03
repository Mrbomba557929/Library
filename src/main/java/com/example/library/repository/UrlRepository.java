package com.example.library.repository;

import com.example.library.domain.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    @Transactional
    @Query(value = """
            WITH e AS (
                INSERT INTO urls (url)
                VALUES (?1)
                RETURNING id, url
            )
                SELECT *
                FROM e
            UNION
                SELECT *
                FROM urls;
            """, nativeQuery = true)
    Url save(String url);
}
