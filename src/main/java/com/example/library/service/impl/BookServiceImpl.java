package com.example.library.service.impl;

import com.example.library.domain.filter.Filter;
import com.example.library.domain.filter.FilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.domain.page.PageRequest;
import com.example.library.domain.page.Pageable;
import com.example.library.domain.sort.Sort;
import com.example.library.domain.sort.SortParameters;
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
    public Pageable<Book> findAll(int page, int count, SortParameters sortParameters) {
        return PageRequest.of(page, count, bookRepository.findAll(), Sort.by(sortParameters));
    }

    @Override
    public Pageable<Book> findAll(int page, int count, FilterParameters filterParameters) {
        List<Book> filteredBooks = Filter.filterBy(filterParameters, bookRepository.findAll());
        return PageRequest.of(page, count, filteredBooks);
    }

    @Override
    public Pageable<Book> findAll(int page, int count, SortParameters sortParameters, FilterParameters filterParameters) {
        List<Book> filteredBooks = Filter.filterBy(filterParameters, bookRepository.findAll());
        return PageRequest.of(page, count, filteredBooks, Sort.by(sortParameters));
    }
}
