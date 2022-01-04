package com.example.library.service.impl;

import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import com.example.library.specification.GenericFilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.exception.NotFoundBookException;
import com.example.library.repository.BookRepository;
import com.example.library.service.BookService;
import com.example.library.specification.GenericFilter;
import com.example.library.specification.GenericSpecificationsBuilder;
import com.google.common.base.Strings;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenericFilter<Book> genericFilter;

    @Override
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> ErrorFactory.exceptionBuilder(ErrorMessage.NOT_FOUND_BOOK)
                        .status(HttpStatus.NOT_FOUND)
                        .build(NotFoundBookException.class));
    }

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Page<Book> findAll(int page, int size, String sort, GenericFilterParameters genericFilterParameters) {

        GenericSpecificationsBuilder<Book> builder = genericFilter.filterBy(genericFilterParameters);

        if (!Strings.isNullOrEmpty(sort)) {
            String[] parameters = sort.split("\\s*,\\s*");
            Sort.Direction direction = Sort.Direction.fromString(parameters[1]);
            return bookRepository.findAll(builder.build(), PageRequest.of(page, size, Sort.by(direction, parameters[0])));
        }

        return bookRepository.findAll(builder.build(), PageRequest.of(page, size));
    }

    @Override
    public Book save(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
