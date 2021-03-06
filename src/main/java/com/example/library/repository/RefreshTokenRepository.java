package com.example.library.repository;

import com.example.library.domain.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {

    @Query(value = """
            SELECT *
            FROM refresh_tokens
            WHERE refresh_tokens.token = ?1""", nativeQuery = true)
    Optional<RefreshToken> findByToken(String token);

    @Transactional
    @Query(value = """
            WITH refresh_token AS
            (
                INSERT INTO refresh_tokens (expiry_date, token, user_id)
                VALUES (?1, ?2, ?3)
                RETURNING *
            )
            SELECT *
            FROM refresh_token""", nativeQuery = true)
    RefreshToken save(Instant expiryDate, String token, Long userId);

    @Transactional
    @Modifying(clearAutomatically = true)
    @Query(value = """
            DELETE FROM refresh_tokens
            WHERE refresh_tokens.user_id = ?1""", nativeQuery = true)
    void deleteByUserId(Long userId);
}
