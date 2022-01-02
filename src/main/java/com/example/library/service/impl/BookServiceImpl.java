package com.example.library.service.impl;

import com.example.library.domain.auxiliary.filter.Filter;
import com.example.library.domain.auxiliary.filter.FilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.domain.auxiliary.page.PageRequest;
import com.example.library.domain.auxiliary.page.Pageable;
import com.example.library.domain.auxiliary.sort.Sort;
import com.example.library.domain.auxiliary.sort.SortParameters;
import com.example.library.exception.NotFoundBookException;
import com.example.library.repository.BookRepository;
import com.example.library.service.AuthorService;
import com.example.library.service.BookService;
import com.example.library.service.GenreService;
import com.example.library.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final UrlService urlService;
    private final AuthorService authorService;
    private final GenreService genreService;
    private final BookRepository bookRepository;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new NotFoundBookException("Error: Book not found!"));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Pageable<Book> findAll(int page, int count) {
        return PageRequest.of(page, count, bookRepository.findAll());
    }

    @Override
    public Pageable<Book> findAll(int page, int count, SortParameters sortParameters, FilterParameters filterParameters) {
        List<Book> books = bookRepository.findAll();

        if (!filterParameters.isEmpty()) {
            books = Filter.filterBy(filterParameters, books);
        }

        if (!sortParameters.isEmpty()) {
            return PageRequest.of(page, count, books, Sort.by(sortParameters));
        }

        return PageRequest.of(page, count, books);
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }
}
