package com.example.library.dtofactory;

import com.example.library.specification.GenericFilterParameters;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class FilterFactory {

    public GenericFilterParameters toFilterParameters(String authors, String genres, LocalDate from, LocalDate to) {
        return GenericFilterParameters.builder()
                .authors(Objects.isNull(authors) ? null : authors.split("\\s*,\\s*"))
                .genres(Objects.isNull(genres) ? null : genres.split("\\s*,\\s*"))
                .from(from)
                .to(to)
                .build();
    }
}
