package com.example.library.service.impl;

import com.example.library.domain.auxiliary.filter.Filter;
import com.example.library.domain.auxiliary.filter.FilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.domain.auxiliary.page.PageRequest;
import com.example.library.domain.auxiliary.page.Pageable;
import com.example.library.domain.auxiliary.sort.Sort;
import com.example.library.domain.auxiliary.sort.SortParameters;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

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
}
