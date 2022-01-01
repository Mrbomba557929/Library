package com.example.library.controller;

import com.example.library.domain.filter.FilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.domain.page.Pageable;
import com.example.library.domain.sort.SortParameters;
import com.example.library.dto.BookDto;
import com.example.library.factory.BookFactory;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookController {

    private static final String FIND_ALL = "/books";
    private static final String FIND_ALL_PAGINATED = "/books/paginated";

    private final BookService bookService;
    private final BookFactory bookFactory;

    @GetMapping(FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<BookDto> books = bookService.findAll()
                .stream()
                .map(bookFactory::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(FIND_ALL_PAGINATED)
    public ResponseEntity<?> findAll(@RequestBody(required = false) FilterParameters filterParameters,
                                     @RequestBody(required = false) SortParameters sortParameters,
                                     @RequestParam("page") int page,
                                     @RequestParam("size") int size) {
        Pageable<Book> books;

        if (Objects.nonNull(filterParameters) && Objects.nonNull(sortParameters)) {
            books = bookService.findAll(page, size, sortParameters, filterParameters);
        } else if (Objects.nonNull(sortParameters)) {
            books = bookService.findAll(page, size, sortParameters);
        } else if (Objects.nonNull(filterParameters)) {
            books = bookService.findAll(page, size, filterParameters);
        } else {
            books = bookService.findAll(page, size);
        }

        return new ResponseEntity<>(books.map(bookFactory::toDto), HttpStatus.OK);
    }

}
