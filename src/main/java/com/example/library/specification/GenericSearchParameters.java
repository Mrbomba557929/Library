package com.example.library.specification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.time.LocalDate;

public record GenericSearchParameters(String[] authors, String[] genres, LocalDate from,
                                      LocalDate to, String search, String sort) {
    @Builder
    @JsonCreator
    public GenericSearchParameters(@JsonProperty("authors") String[] authors, @JsonProperty("genres") String[] genres,
                                   @JsonProperty("from") LocalDate from, @JsonProperty("to") LocalDate to,
                                   @JsonProperty("search") String search, @JsonProperty("sort") String sort) {
        this.sort = sort;
        this.authors = authors;
        this.genres = genres;
        this.from = from;
        this.to = to;
        this.search = search;
    }
}
