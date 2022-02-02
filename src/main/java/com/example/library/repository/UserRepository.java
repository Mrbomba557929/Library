package com.example.library.repository;

import com.example.library.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT *
            FROM users
            WHERE users.email = ?1""", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Modifying(clearAutomatically = true)
    @Query(value = """
                INSERT INTO users_authorities (user_id, authority_id)
                VALUES (?1, ?2)""", nativeQuery = true)
    void addAuthority(Long userId, Long authorityId);

    @Query(value = """
            SELECT
                CASE WHEN EXISTS
                (
                    SELECT *
                    FROM users
                    WHERE users.email = ?1
                )
                THEN true
                ELSE false
            END
            """, nativeQuery = true)
    Boolean existsByEmail(String email);
}
