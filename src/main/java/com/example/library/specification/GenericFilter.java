package com.example.library.specification;

import com.example.library.exception.business.IllegalStateFilterException;
import com.example.library.exception.factory.ErrorFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Objects;

import static com.example.library.exception.factory.ErrorMessage.ILLEGAL_STATE_FILTER;
import static com.example.library.specification.SpecificationOperation.*;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

@Component
public class GenericFilter<T> {

    public GenericSpecificationsBuilder<T> filterBy(GenericSearchParameters parameters) {

        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();

        try {

            for (Field field : parameters.getClass().getDeclaredFields()) {
                field.setAccessible(true);
                Object valueField = field.get(parameters);

                if (Objects.nonNull(valueField)) {
                    switch (field.getName()) {
                        case "authors" ->
                                builder.with("authors", "fio", false, EQUALLY, asList((String[]) valueField));
                        case "genres" ->
                                builder.with("genre", "genre", false, EQUALLY, asList((String[]) valueField));
                        case "from" ->
                                builder.with("creationAt", false, GREATER_THAN_OR_EQUALLY, singletonList(valueField));
                        case "to" ->
                                builder.with("creationAt", false, LESS_THAN_OR_EQUALLY, singletonList(valueField));
                        case "search" ->
                                builder.with("name", false, LIKE, singletonList("%" + valueField + "%"));
                    }
                }
            }

        } catch (IllegalStateException | IllegalAccessException e) {
           throw ErrorFactory.exceptionBuilder(ILLEGAL_STATE_FILTER)
                   .status(EXPECTATION_FAILED)
                   .link("GenericFilter/filterBy")
                   .build(IllegalStateFilterException.class);
        }

        return builder;
    }
}
