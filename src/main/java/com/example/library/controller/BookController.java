package com.example.library.controller;

import com.example.library.domain.model.Book;
import com.example.library.domain.dto.BookDto;
import com.example.library.dtofactory.BookFactory;
import com.example.library.dtofactory.SpecificationFactory;
import com.example.library.service.BookService;
import com.example.library.specification.GenericSearchParameters;
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

    private static final String DEFAULT_URL = "/books";
    private static final String PAGINATE_ALL_BOOKS = "/books/paginated";

    private final BookService bookService;
    private final BookFactory bookFactory;
    private final SpecificationFactory specificationFactory;

    @GetMapping(DEFAULT_URL)
    public ResponseEntity<?> findAll() {
        List<BookDto> books = bookService.findAll()
                .stream()
                .map(bookFactory::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping(DEFAULT_URL)
    public ResponseEntity<?> save(@Validated @RequestBody BookDto bookDto) {
        Book book = bookFactory.toEntity(bookDto);
        return new ResponseEntity<>(bookFactory.toDto(bookService.save(book)), HttpStatus.OK);
    }

    @PutMapping(DEFAULT_URL)
    public ResponseEntity<?> edit(@Validated @RequestBody BookDto bookDto) {
        Book book = bookFactory.toEntity(bookDto);
        return new ResponseEntity<>(bookFactory.toDto(bookService.edit(book)), HttpStatus.OK);
    }

    @DeleteMapping(DEFAULT_URL)
    public ResponseEntity<?> deleteById(@RequestParam("id") Long id) {
        bookService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(PAGINATE_ALL_BOOKS)
    public ResponseEntity<?> findAll(@RequestParam(name = "sort", required = false) String sort,
                                     @RequestParam(name = "authors", required = false) String authors,
                                     @RequestParam(name = "genres", required = false) String genres,
                                     @RequestParam(name = "from", required = false) LocalDate from,
                                     @RequestParam(name = "to", required = false) LocalDate to,
                                     @RequestParam(name = "search", required = false, defaultValue = "") String search,
                                     @RequestParam("page") int page,
                                     @RequestParam("count") int count) {
        GenericSearchParameters parameters = specificationFactory.toSearchParameters(authors, genres, from, to, search, sort);
        Page<BookDto> books = bookService.findAll(page, count, parameters).map(bookFactory::toDto);
        return new ResponseEntity<>(books, HttpStatus.OK);
    }
}
