package com.example.library.service;

import com.example.library.domain.model.Genre;

import java.util.List;

/**
 * Interface for the {@link Genre} entity.
 */
public interface GenreService {

    /**
     * Method to find all the {@link Genre} entities.
     *
     * @return list of the {@link Genre} entities.
     */
    List<Genre> findAll();
}
