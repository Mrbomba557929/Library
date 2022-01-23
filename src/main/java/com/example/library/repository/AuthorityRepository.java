package com.example.library.repository;

import com.example.library.domain.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, Long> {

    @Query(value = """
            SELECT *
            FROM authorities
            WHERE authorities.role = ?1""", nativeQuery = true)
    Optional<Authority> findByRole(String role);
}
