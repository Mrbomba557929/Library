package com.example.library.service;

import com.example.library.domain.auxiliary.filter.FilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.domain.auxiliary.page.Pageable;
import com.example.library.domain.auxiliary.sort.SortParameters;

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
     * @return list of a {@link Book} entities on page.
     */
    Pageable<Book> findAll(int page, int count);

    /**
     * Method to find all a {@link Book} entities.
     *
     * @param page - current number of page.
     * @param count - count of elements on page.
     * @param sortParameters - values to sort.
     * @param filterParameters - values to filter
     * @return list of a {@link Book} entities on page.
     */
    Pageable<Book> findAll(int page, int count, SortParameters sortParameters, FilterParameters filterParameters);

    /**
     * Method to save the {@link Book} entity.
     *
     * @param book - the {@link Book} entity.
     * @return saved the {@link Book} entity.
     */
    Book save(Book book);

    /**
     * Method to delete the {@link Book} by id.
     *
     * @param id - the id of the {@link Book} entity.
     */
    void deleteById(Long id);
}
