package com.example.library.controller;

import com.example.library.domain.auxiliary.FilterSortRequest;
import com.example.library.domain.model.Book;
import com.example.library.domain.auxiliary.page.Pageable;
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
    public ResponseEntity<?> findAll(@RequestBody(required = false) FilterSortRequest request,
                                     @RequestParam("page") int page,
                                     @RequestParam("size") int size) {

        Pageable<Book> books = Objects.isNull(request) ?
                bookService.findAll(page, size) :
                bookService.findAll(page, size, request.getSort(), request.getFilter());

        return new ResponseEntity<>(books.map(bookFactory::toDto), HttpStatus.OK);
    }
}
