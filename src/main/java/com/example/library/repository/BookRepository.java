package com.example.library.repository;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.domain.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long>, JpaSpecificationExecutor<Book> {

    @Query(value = """
            SELECT new com.example.library.domain.dto.base.BookCreationDate(book.creationAt)
            FROM Book book""")
    List<BookCreationDate> getCreationDates();
}