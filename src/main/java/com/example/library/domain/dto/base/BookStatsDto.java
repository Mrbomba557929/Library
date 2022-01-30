package com.example.library.domain.dto.base;

import lombok.Builder;

public record BookStatsDto(BookAdditionStatsDto bookAdditionStatsDto, BookSearchStatsDto bookSearchStatsDto,
                           BookCountDto bookCountDto) {

    @Builder
    public BookStatsDto {
    }


    public static record BookAdditionStatsDto(Long numberOfBooksForToday, Long numberOfBooksForWeek,
                                       Long numberOfBooksForMouth, Long numberOfBooksForYear) {

        @Builder
        public BookAdditionStatsDto {
        }
    }


    public static record BookSearchStatsDto(Long numberOfSearchesBooksForToday, Long numberOfSearchesBooksForWeek,
                                              Long numberOfSearchesBooksForYear, Long numberOfSearchesBooksForAllTime) {

        @Builder
        public BookSearchStatsDto {
        }
    }


    public static record BookCountDto(String message, long count) {

        @Builder
        public BookCountDto {
        }
    }
}
