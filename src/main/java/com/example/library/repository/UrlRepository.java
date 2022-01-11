package com.example.library.repository;

import com.example.library.domain.model.Url;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

    @Modifying
    @Query(value = """
            INSERT INTO urls
            VALUES (?1)
            """, nativeQuery = true)
    Url save(String url);
}
