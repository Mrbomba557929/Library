package com.example.library.controller;

import com.example.library.domain.auxiliary.FilterSortRequest;
import com.example.library.domain.model.Book;
import com.example.library.domain.auxiliary.page.Pageable;
import com.example.library.dto.BookDto;
import com.example.library.factory.BookFactory;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Log4j2
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookController {

    private static final String FIND_ALL = "/books";
    private static final String PAGINATE_ALL_BOOKS = "/books/paginated";
    private static final String ADD_BOOK = "/books/add";
    private static final String DELETE_BY_ID = "/books";

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

    @PostMapping(ADD_BOOK)
    public ResponseEntity<?> addBook(@Validated @RequestBody BookDto bookDto) {
        Book book = bookFactory.toEntity(bookDto);
        return new ResponseEntity<>(bookFactory.toDto(bookService.save(book)), HttpStatus.OK);
    }

    @DeleteMapping(DELETE_BY_ID)
    public ResponseEntity<?> deleteById(@RequestParam("id") Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping(PAGINATE_ALL_BOOKS)
    public ResponseEntity<?> paginate(@RequestBody(required = false) FilterSortRequest request,
                                      @RequestParam("page") int page,
                                      @RequestParam("size") int size) {

        Pageable<Book> books = Objects.isNull(request) ?
                bookService.findAll(page - 1, size) :
                bookService.findAll(page - 1, size, request.getSort(), request.getFilter());

        return new ResponseEntity<>(books.map(bookFactory::toDto), HttpStatus.OK);
    }
}
