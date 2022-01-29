package com.example.library.service;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.domain.model.Book;
import com.example.library.specification.GenericSearchParameters;
import org.springframework.data.domain.Page;

import java.util.List;

public interface BookService {

    /**
     * Method to find by id the {@link Book} entity.
     *
     * @param id - the id of the {@link Book} entity.
     * @return founded the {@link Book} entity.
     */
    Book findById(Long id);

    /**
     * Method to find all a {@link Book} entities.
     *
     * @return list of a {@link Book} entities on page.
     */
    List<Book> findAll();

    /**
     * Method to find all a {@link Book} entities.
     *
     * @param page - current number of page.
     * @param count - count of elements on page.
     * @param parameters - search parameters.
     * @return list of a {@link Book} entities on page.
     */
    Page<Book> findAll(int page, int count, GenericSearchParameters parameters);

    /**
     * Method to get creation dates of the books.
     *
     * @return list of creation dates of the books.
     */
    List<BookCreationDate> getCreationDates();

    /**
     * Method to save the {@link Book} entity.
     *
     * @param book - the {@link Book} entity.
     * @return saved the {@link Book} entity.
     */
    Book save(Book book);

    /**
     * Method to edit the {@link Book} entity.
     *
     * @param source - source the {@link Book} entity.
     * @return edited the {@link Book} entity.
     */
    Book edit(Book source);

    /**
     * Method to delete the {@link Book} by id.
     *
     * @param id - the id of the {@link Book} entity.
     */
    void deleteById(Long id);
}
