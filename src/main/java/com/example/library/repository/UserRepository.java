package com.example.library.repository;

import com.example.library.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = """
            SELECT *
            FROM users
            WHERE users.email = ?1""", nativeQuery = true)
    Optional<User> findByEmail(String email);

    @Query(value = """
            WITH users_authorities AS (
                INSERT INTO users_authorities (user_id, authority_id)
                VALUES (?1, ?2)
                RETURNING user_id, authority_id
            )
            SELECT u.id, u.email, u.password,
                   a.id, a.role
            FROM users_authorities AS u_a
            LEFT OUTER JOIN users u ON u.id = u_a.user_id
            LEFT OUTER JOIN authorities a on a.id = u_a.authority_id""", nativeQuery = true)
    Optional<User> addAuthorityAndReturnUser(Long userId, Long authorityId);
    // доделать, хибернейт нихуя не асорити не достает, ему по хуй на него, хотя sql запрос работает.
    // Видимо хибер долабеб и придется писать отдельные запросы.

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
