package com.example.library.mapper;

import com.example.library.specification.GenericSearchParameters;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class SpecificationMapper {

    public GenericSearchParameters toSearchParameters(String authors, String genres, Integer from,
                                                      Integer to, String search, String sort) {
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