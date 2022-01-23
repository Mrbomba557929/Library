package com.example.library.repository;

import com.example.library.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query(value = """
            SELECT *
            FROM refresh_tokens
            WHERE refresh_tokens.id = ?1""", nativeQuery = true)
    Optional<RefreshToken> findById(Long id);

    @Query(value = """
            SELECT *
            FROM refresh_tokens
            WHERE refresh_tokens.token = ?1""", nativeQuery = true)
    Optional<RefreshToken> findByToken(String token);

    @Query(value = """
            INSERT INTO refresh_tokens (expiry_date, token, user_id)
                VALUES (?1, ?2, ?3)
            RETURNING *""", nativeQuery = true)
    RefreshToken save(Instant expiryDate, String token, Long userId);

    @Query(value = """
            DELETE FROM refresh_tokens
            WHERE refresh_tokens.user_id = ?1""", nativeQuery = true)
    void deleteByUserId(Long userId);
}
