package com.example.library.service;

import com.example.library.domain.filter.FilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.domain.page.Page;
import com.example.library.domain.page.Pageable;
import com.example.library.domain.sort.SortParameters;

import java.util.List;

public interface BookService {

    /**
     * Method designed to find all a {@link Book} entities.
     *
     * @return list of a {@link Book} entities on page.
     */
    List<Book> findAll();

    /**
     * Method designed to find all a {@link Book} entities.
     *
     * @param page - current number of page.
     * @param count - count of elements on page.
     * @return list of a {@link Book} entities on page.
     */
    Pageable<Book> findAll(int page, int count);

    /**
     * Method designed to find all a {@link Book} entities.
     *
     * @param page - current number of page.
     * @param count - count of elements on page.
     * @param sortParameters - values to sort.
     * @return list of a {@link Book} entities on page.
     */
    Pageable<Book> findAll(int page, int count, SortParameters sortParameters);

    /**
     * Method designed to find all a {@link Book} entities.
     *
     * @param page - current number of page.
     * @param count - count of elements on page.
     * @param filterParameters - values to filter
     * @return list of a {@link Book} entities on page.
     */
    Pageable<Book> findAll(int page, int count, FilterParameters filterParameters);

    /**
     * Method designed to find all a {@link Book} entities.
     *
     * @param page - current number of page.
     * @param count - count of elements on page.
     * @param sortParameters - values to sort.
     * @param filterParameters - values to filter
     * @return list of a {@link Book} entities on page.
     */
    Pageable<Book> findAll(int page, int count, SortParameters sortParameters, FilterParameters filterParameters);
}
