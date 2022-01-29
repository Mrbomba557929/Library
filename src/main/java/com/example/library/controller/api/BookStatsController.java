package com.example.library.controller.api;

import com.example.library.domain.dto.base.BookStatsDto;
import com.example.library.mapper.BookStatsMapper;
import com.example.library.service.BookStatsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Log4j2
@CrossOrigin
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1")
public class BookStatsController {

    private final static String GET_STATS = "/book/stats";
    private final static String GET_ADDITION_BOOK_STATS = "/book/stats/addition";
    private final static String GET_SEARCH_BOOK_STATS = "/book/stats/search";
    private static final String GET_COUNT_ALL_BOOKS = "/books/stats/countAll";

    private final BookStatsService bookStatsService;
    private final BookStatsMapper bookStatsMapper;

    @GetMapping(GET_STATS)
    public ResponseEntity<?> getStats() {
        BookStatsDto.BookAdditionStatsDto bookAdditionStatsDto = bookStatsMapper.toBookAdditionStatsDto(
                bookStatsService.getNumberOfAddedBooksForToday(),
                bookStatsService.getNumberOfAddedBooksForWeek(),
                bookStatsService.getNumberOfAddedBooksForMonth(),
                bookStatsService.getNumberOfAddedBooksForYear()
        );

        BookStatsDto.BookSearchStatsDto bookSearchStatsDto = bookStatsMapper.toBookSearchStatsDto(
                bookStatsService.getNumberOfSearchesBooksForToday(),
                bookStatsService.getNumberOfSearchesBooksForMonth(),
                bookStatsService.getNumberOfSearchesBooksForYear(),
                bookStatsService.getNumberOfSearchesBooksForAllTime()
        );

        BookStatsDto.BookCountDto bookCountDto = bookStatsMapper.toBookCountResponse(bookStatsService.getNumberOfAllBooks());

        return new ResponseEntity<>(bookStatsMapper.toDto(bookAdditionStatsDto, bookSearchStatsDto, bookCountDto), HttpStatus.OK);
    }

    @GetMapping(GET_ADDITION_BOOK_STATS)
    public ResponseEntity<?> getAdditionBooksStats() {
        BookStatsDto.BookAdditionStatsDto bookAdditionStatsDto = bookStatsMapper.toBookAdditionStatsDto(
                bookStatsService.getNumberOfAddedBooksForToday(),
                bookStatsService.getNumberOfAddedBooksForWeek(),
                bookStatsService.getNumberOfAddedBooksForMonth(),
                bookStatsService.getNumberOfAddedBooksForYear()
        );

        return new ResponseEntity<>(bookAdditionStatsDto, HttpStatus.OK);
    }

    @GetMapping(GET_SEARCH_BOOK_STATS)
    public ResponseEntity<?> getSearchBooksStats() {
        BookStatsDto.BookSearchStatsDto bookSearchStatsDto = bookStatsMapper.toBookSearchStatsDto(
                bookStatsService.getNumberOfSearchesBooksForToday(),
                bookStatsService.getNumberOfSearchesBooksForMonth(),
                bookStatsService.getNumberOfSearchesBooksForYear(),
                bookStatsService.getNumberOfSearchesBooksForAllTime()
        );

        return new ResponseEntity<>(bookSearchStatsDto, HttpStatus.OK);
    }

    @GetMapping(GET_COUNT_ALL_BOOKS)
    public ResponseEntity<?> getCountAllBooks() {
        BookStatsDto.BookCountDto bookCountDto = bookStatsMapper.toBookCountResponse(bookStatsService.getNumberOfAllBooks());
        return new ResponseEntity<>(bookCountDto, HttpStatus.OK);
    }
}
