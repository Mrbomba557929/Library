package com.example.library.service.impl;

import com.example.library.domain.dto.base.BookCreationDate;
import com.example.library.exception.business.FailedToSaveException;
import com.example.library.exception.business.NotFound;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.service.AuthorService;
import com.example.library.service.GenreService;
import com.example.library.domain.model.Book;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.specification.GenericFilter;
import com.example.library.specification.GenericSearchParameters;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.library.exception.factory.ErrorMessage.NOT_FOUND_BOOK;
import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreService genreService;
    private final AuthorService authorService;
    private final GenericFilter<Book> filter;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() ->
                        ErrorFactory.exceptionBuilder(NOT_FOUND_BOOK)
                                .status(NOT_FOUND)
                                .link("BookServiceImpl/findById")
                                .build(NotFound.class)
                );
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findAll(int page, int count, GenericSearchParameters parameters) {
        Specification<Book> specification = filter.filterBy(parameters).build();

        if (!Strings.isNullOrEmpty(parameters.sort())) {
            String[] sortParameters = parameters.sort().split("\\s*,\\s*");
            Sort.Direction direction = Sort.Direction.fromString(sortParameters[1]);

            if (sortParameters[0].equalsIgnoreCase("authors")) {
                return bookRepository.findAllSortedByFirstElementFromAuthorsList(direction.name(), PageRequest.of(page, count));
            }

            return bookRepository.findAll(specification, PageRequest.of(page, count, Sort.by(direction, sortParameters[0])));
        }

        return bookRepository.findAll(specification, PageRequest.of(page, count));
    }

    @Override
    public List<BookCreationDate> getCreationDates() {
        return bookRepository.getCreationDates();
    }

    @Override
    public Book save(Book book) {
        book.setAuthors(authorService.saveAll(book.getAuthors()));
        book.setGenre(genreService.save(book.getGenre()));

        try {
            return bookRepository.save(book);
        } catch (DataAccessException e) {
            throw ErrorFactory.exceptionBuilder(e.getMessage())
                    .status(EXPECTATION_FAILED)
                    .link("BookServiceImpl/save")
                    .build(FailedToSaveException.class);
        }
    }

    @Override
    public Book edit(Book source) {
        Optional<Book> bookOptional = bookRepository.findById(source.getId());

        if (bookOptional.isPresent()) {
            return save(source);
        }

        throw ErrorFactory.exceptionBuilder(NOT_FOUND_BOOK)
                .status(NOT_FOUND)
                .link("BookServiceImpl/edit")
                .build(NotFound.class);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
