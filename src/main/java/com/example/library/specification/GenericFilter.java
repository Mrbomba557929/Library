package com.example.library.specification;

import com.example.library.exception.IllegalStateFilterException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

import static com.example.library.specification.SpecificationOperation.*;

@Component
public class GenericFilter<T> {

    public <U extends GenericFilterParameters> GenericSpecificationsBuilder<T> filterBy(U filterParameters) {
        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
        Field[] fields = filterParameters.getClass().getDeclaredFields();

        try {

            for (Field field : fields) {
                field.setAccessible(true);
                Object valueField = field.get(filterParameters);

                if (Objects.nonNull(valueField)) {
                    switch (field.getName()) {
                        case "authors" ->
                                builder.with("authors", "fio", true, EQUALLY, Arrays.asList((String[]) valueField));
                        case "genres" ->
                                builder.with("genre", "genre", true, EQUALLY, Arrays.asList((String[]) valueField));
                        case "from" ->
                                builder.with("createdAt", true, GREATER_THAN_OR_EQUALLY, Collections.singletonList(valueField));
                        case "to" ->
                                builder.with("createdAt", true, LESS_THAN_OR_EQUALLY, Collections.singletonList(valueField));
                        case "search" ->
                                builder.with("name", false, LIKE, Collections.singletonList("%" + valueField + "%"));
                    }
                }
            }

        } catch (IllegalStateException | IllegalAccessException e) {
           throw ErrorFactory.exceptionBuilder(ErrorMessage.ILLEGAL_STATE_FILTER)
                   .status(HttpStatus.EXPECTATION_FAILED)
                   .build(IllegalStateFilterException.class);
        }

        return builder;
    }
}
