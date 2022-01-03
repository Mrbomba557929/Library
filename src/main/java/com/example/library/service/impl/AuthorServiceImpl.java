package com.example.library.service.impl;

import com.example.library.domain.model.Author;
import com.example.library.repository.AuthorRepository;
import com.example.library.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        return authors.stream()
                .map(author -> authorRepository.findByFirstNameAndLastName(author.getFirstName(), author.getLastName())
                        .orElse(authorRepository.save(author)))
                .collect(Collectors.toList());
    }

    @Override
    public void addBookToAuthors(Long bookId, List<Author> authors) {
        authors.forEach(author -> authorRepository.addBook(bookId, author.getId()));
    }
}
