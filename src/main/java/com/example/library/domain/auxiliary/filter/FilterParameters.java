package com.example.library.domain.auxiliary.filter;

import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Data
public class FilterParameters {

    private List<String> authors;
    private List<String> genres;
    private Instant from;
    private Instant to;

    public FilterParameters() {
        authors = new ArrayList<>();
        genres = new ArrayList<>();
    }
}
