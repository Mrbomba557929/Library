package com.example.library.factory;

import com.example.library.specification.GenericFilterParameters;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class FilterFactory {

    public GenericFilterParameters toFilterParameters(String[] authors, String[] genres, LocalDate from, LocalDate to) {
        return GenericFilterParameters.builder()
                .authors(authors)
                .genres(genres)
                .from(from)
                .to(to)
                .build();
    }
}
