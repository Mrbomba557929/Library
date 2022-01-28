package com.example.library.repository;

import com.example.library.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT users.id, users.email, users.password
            FROM users
            INNER JOIN users_authorities ua on users.id = ua.user_id
            INNER JOIN authorities a on ua.authority_id = a.id
            WHERE users.email = ?1""", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = """
            SELECT users.id, users.email, users.password
            FROM users
            INNER JOIN users_authorities ua on users.id = ua.user_id
            INNER JOIN authorities a on ua.authority_id = a.id
            WHERE users.id = ?1""", nativeQuery = true)
    Optional<User> findById(Long id);

    @Query(value = """
            SELECT
                CASE WHEN EXISTS
                (
                    SELECT * FROM users WHERE users.email = ?1
                )
                THEN true
                ELSE false
            END
            """, nativeQuery = true)
    Boolean existsByEmail(String email);

    @Query(value = """
           WITH e AS (
                INSERT INTO users (email, password)
                VALUES (?1, ?2)
                RETURNING id, email, password
           )
           SELECT * FROM e
           """, nativeQuery = true)
    User save(String email, String password);

    @Transactional
    @Modifying
    @Query(value = """
            INSERT INTO users_authorities (user_id, authority_id)
            VALUES (?1, ?2);
            """, nativeQuery = true)
    void addAuthority(Long userId, Long authorityId);
}
