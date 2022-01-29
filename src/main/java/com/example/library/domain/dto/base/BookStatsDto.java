package com.example.library.domain.dto.base;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

public record BookStatsDto(BookAdditionStatsDto bookAdditionStatsDto, BookSearchStatsDto bookSearchStatsDto,
                           BookCountDto bookCountDto) {

    @Builder
    public BookStatsDto {
    }

    @Setter
    @Getter
    public static record BookAdditionStatsDto(Long numberOfBooksForToday, Long numberOfBooksForWeek,
                                       Long numberOfBooksForMouth, Long numberOfBooksForYear) {

        @Builder
        public BookAdditionStatsDto {
        }
    }

    @Setter
    @Getter
    public static record BookSearchStatsDto(Long numberOfSearchesBooksForToday, Long numberOfSearchesBooksForWeek,
                                              Long numberOfSearchesBooksForYear, Long numberOfSearchesBooksForAllTime) {

        @Builder
        public BookSearchStatsDto {
        }
    }

    @Setter
    @Getter
    public static record BookCountDto(String message, long count) {

        @Builder
        public BookCountDto {
        }
    }
}
