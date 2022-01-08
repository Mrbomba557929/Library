package com.example.library.dtofactory;

import com.example.library.specification.GenericSearchParameters;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Objects;

@Component
public class SpecificationFactory {

    public GenericSearchParameters toSearchParameters(String authors, String genres, LocalDate from,
                                                      LocalDate to, String search, String sort) {
        return GenericSearchParameters.builder()
                .sort(sort)
                .authors(Objects.isNull(authors) ? null : authors.split("\\s*,\\s*"))
                .genres(Objects.isNull(genres) ? null : genres.split("\\s*,\\s*"))
                .from(from)
                .to(to)
                .search(search)
                .build();
    }
}
