package com.example.library.service.impl;

import com.example.library.domain.model.Author;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

/**
 * Implementation the {@link AuthorService} interface
 */
@RequiredArgsConstructor
@Component
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Override
    public List<Author> saveAll(List<Author> authors) {
        try {
            return authors.stream()
                    .map(author -> authorRepository.save(author.getFio()))
                    .collect(Collectors.toList());
        } catch (DataAccessException e) {
            throw ErrorFactory.exceptionBuilder(e.getMessage())
                    .status(EXPECTATION_FAILED)
                    .build(FailedToSaveException.class);
        }
    }

    @Override
    public void addBookToAuthors(Long bookId, List<Author> authors) {
        authors.forEach(author -> authorRepository.addBook(bookId, author.getId()));
    }
}
