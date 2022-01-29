package com.example.library.mapper;

import com.example.library.domain.dto.base.BookStatsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookStatsMapper {

    public BookStatsDto toDto(BookStatsDto.BookAdditionStatsDto bookAdditionStatsDto, BookStatsDto.BookSearchStatsDto bookSearchStatsDto,
                              BookStatsDto.BookCountDto bookCountDto) {
        return BookStatsDto.builder()
                .bookAdditionStatsDto(bookAdditionStatsDto)
                .bookSearchStatsDto(bookSearchStatsDto)
                .bookCountDto(bookCountDto)
                .build();
    }

    public BookStatsDto.BookAdditionStatsDto toBookAdditionStatsDto(Long numberOfBooksForToday, Long numberOfBooksForWeek,
                                                                    Long numberOfBooksForMouth, Long numberOfBooksForYear) {
        return BookStatsDto.BookAdditionStatsDto.builder()
                .numberOfBooksForToday(numberOfBooksForToday)
                .numberOfBooksForWeek(numberOfBooksForWeek)
                .numberOfBooksForMouth(numberOfBooksForMouth)
                .numberOfBooksForYear(numberOfBooksForYear)
                .build();
    }

    public BookStatsDto.BookSearchStatsDto toBookSearchStatsDto(Long numberOfSearchesBooksForToday, Long numberOfSearchesBooksForWeek,
                                                                Long numberOfSearchesBooksForYear, Long numberOfSearchesBooksForAllTime) {
        return BookStatsDto.BookSearchStatsDto.builder()
                .numberOfSearchesBooksForToday(numberOfSearchesBooksForToday)
                .numberOfSearchesBooksForWeek(numberOfSearchesBooksForWeek)
                .numberOfSearchesBooksForYear(numberOfSearchesBooksForYear)
                .numberOfSearchesBooksForAllTime(numberOfSearchesBooksForAllTime)
                .build();
    }

    public BookStatsDto.BookCountDto toBookCountResponse(long countAllBooks) {
        return BookStatsDto.BookCountDto.builder()
                .count(countAllBooks)
                .message("Count all books")
                .build();
    }
}
