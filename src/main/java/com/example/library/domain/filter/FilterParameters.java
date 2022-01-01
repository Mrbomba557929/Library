package com.example.library.domain.filter;

import lombok.Data;

import java.time.Instant;
import java.util.List;

@Data
public class FilterParameters {
    private List<String> authors;
    private List<String> genres;
    private Instant from;
    private Instant to;
}
