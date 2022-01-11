package com.example.library.service.impl;

import com.example.library.domain.model.Genre;
import com.example.library.repository.GenreRepository;
import com.example.library.service.GenreService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

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
        return genreRepository.save(genre.getGenre());
    }
}
