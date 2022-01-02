package com.example.library.service;

import com.example.library.domain.model.Author;
import com.example.library.domain.model.Book;

import java.util.List;

/**
 * Interface for {@link Author} entity.
 */
public interface AuthorService {

    /**
     * Method to find all the {@link Author} entities.
     *
     * @return list of the {@link Author} entities.
     */
    List<Author> findAll();

    /**
     * Method to save all the {@link Author} entities.
     *
     * @param authors - list of the {@link Author} entities.
     * @return list of the {@link Author} entities.
     */
    List<Author> saveAll(List<Author> authors);

    /**
     * Method to add the {@link Book} to the {@link Author} entities.
     *
     * @param idBook - the id of the {@link Book} entity.
     * @param authors - the list of the {@link Author} entities.
     */
    void addBookToAuthors(Long idBook, List<Author> authors);
}
