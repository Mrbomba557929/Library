package com.example.library.specification;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class GenericFilterParameters {
    private String[] authors;
    private String[] genres;
    private LocalDate from;
    private LocalDate to;
}
