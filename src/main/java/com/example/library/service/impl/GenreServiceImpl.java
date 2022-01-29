package com.example.library.service.impl;

import com.example.library.domain.model.Genre;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.repository.GenreRepository;
import com.example.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * Implementation the {@link GenreService} interface.
 */
@RequiredArgsConstructor
@Component
public class GenreServiceImpl implements GenreService {

    private final GenreRepository genreRepository;

    @Override
    public List<Genre> findAll() {
        return genreRepository.findAll();
    }

    @Override
    public Genre save(Genre genre) {
        try {
            return genreRepository.save(genre.getGenre());
        } catch (DataAccessException e) {
            throw ErrorFactory.exceptionBuilder(e.getMessage())
                    .status(BAD_REQUEST)
                    .link("GenreServiceImpl/save")
                    .build(FailedToSaveException.class);
        }
    }
}
