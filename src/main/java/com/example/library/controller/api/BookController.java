package com.example.library.controller.api;

import com.example.library.domain.model.Book;
import com.example.library.domain.dto.base.BookDto;
import com.example.library.mapper.BookMapper;
import com.example.library.mapper.SpecificationMapper;
import com.example.library.service.BookService;
import com.example.library.service.BookStatsService;
import com.example.library.specification.GenericSearchParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    private static final String GET_ALL_CREATION_DATES = "/books/creationDates";

    private final BookService bookService;
    private final BookMapper bookMapper;
    private final SpecificationMapper specificationMapper;
    private final BookStatsService bookStatsService;

    @GetMapping(DEFAULT_URL)
    public ResponseEntity<?> findAll() {
        List<BookDto> books = bookService.findAll()
                .stream()
                .map(bookMapper::toDto)
                .collect(Collectors.toList());

        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @PostMapping(DEFAULT_URL)
    public ResponseEntity<?> save(@Valid @RequestBody BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        return new ResponseEntity<>(bookMapper.toDto(bookService.save(book)), HttpStatus.OK);
    }

    @PutMapping(DEFAULT_URL)
    public ResponseEntity<?> edit(@Valid @RequestBody BookDto bookDto) {
        Book book = bookMapper.toEntity(bookDto);
        return new ResponseEntity<>(bookMapper.toDto(bookService.edit(book)), HttpStatus.OK);
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
                                     @RequestParam(name = "from", required = false) Integer from,
                                     @RequestParam(name = "to", required = false) Integer to,
                                     @RequestParam(name = "search", required = false) String search,
                                     @RequestParam("page") int page,
                                     @RequestParam("count") int count) {
        GenericSearchParameters parameters = specificationMapper.toSearchParameters(authors, genres, from, to, search, sort);
        Page<BookDto> books = bookService.findAll(page, count, parameters).map(bookMapper::toDto);
        bookStatsService.increaseCounter();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping(GET_ALL_CREATION_DATES)
    public ResponseEntity<?> getCreationDates() {
        return new ResponseEntity<>(bookService.getCreationDates(), HttpStatus.OK);
    }
}
