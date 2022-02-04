package com.example.library.specification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record GenericSearchParameters(String[] authors, String[] genres, Integer from,
                                      Integer to, String search, Sort sort) {
    @Builder
    @JsonCreator
    public GenericSearchParameters(@JsonProperty("authors") String[] authors, @JsonProperty("genres") String[] genres,
                                   @JsonProperty("from") Integer from, @JsonProperty("to") Integer to,
                                   @JsonProperty("search") String search, @JsonProperty("sort") Sort sort) {
        this.authors = authors;
        this.genres = genres;
        this.from = from;
        this.to = to;
        this.search = search;
        this.sort = sort;
    }

    public record Sort (String field, org.springframework.data.domain.Sort.Direction direction) {}
}
