package com.example.library.domain.auxiliary.filter;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
public class FilterParameters {

    private List<String> authors;
    private List<String> genres;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate from;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate to;

    public FilterParameters() {
        authors = new ArrayList<>();
        genres = new ArrayList<>();
    }

    public boolean isEmpty() {
        return authors.isEmpty() &&
               genres.isEmpty() &&
               Objects.isNull(from) &&
               Objects.isNull(to);
    }
}
