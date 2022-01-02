package com.example.library.service;

import com.example.library.domain.model.Url;

/**
 * Service for {@link Url} entity.
 */
public interface UrlService {

    /**
     * Method to save the {@link Url} entity.
     *
     * @param url - the {@link Url} entity.
     * @return saved the {@link Url} entity.
     */
    Url save(Url url);
}
