package com.example.library.controller;

import com.example.library.specification.GenericFilterParameters;
import com.example.library.domain.model.Book;
import com.example.library.domain.dto.BookDto;
import com.example.library.dtofactory.BookFactory;
import com.example.library.dtofactory.FilterFactory;
import com.example.library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
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
    private final FilterFactory filterFactory;

    @GetMapping(FIND_ALL)
    public ResponseEntity<?> findAll() {
        List<BookDto> books = bookService.findAll()
                .stream()
                .map(bookFactory::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(PAGINATE_ALL_BOOKS)
    public ResponseEntity<?> findAll(@RequestParam(name = "authors", required = false) String authors,
                                     @RequestParam(name = "genres", required = false) String genres,
                                     @RequestParam(name = "from", required = false) LocalDate from,
                                     @RequestParam(name = "to", required = false) LocalDate to,
                                     @RequestParam(name = "sort", required = false) String sort,
                                     @RequestParam("page") int page,
                                     @RequestParam("size") int size) {
        GenericFilterParameters genericFilterParameters = filterFactory.toFilterParameters(authors, genres, from, to);
        Page<BookDto> books = bookService.findAll(page, size, sort, genericFilterParameters).map(bookFactory::toDto);
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
}
