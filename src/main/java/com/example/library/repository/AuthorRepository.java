package com.example.library.repository;

import com.example.library.domain.model.Author;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {

    @Query(value = "SELECT * FROM authors WHERE authors.first_name = ?1 AND authors.last_name = ?2", nativeQuery = true)
    Optional<Author> findByFirstNameAndLastName(String firstName, String lastName);

    @Modifying
    @Query(value = "INSERT INTO authors_books (author_id, book_id) VALUES (?2, ?1)", nativeQuery = true)
    void addBook(Long idBook, Long idAuthor);
}
