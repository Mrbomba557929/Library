package com.example.library.specification;

import com.example.library.exception.IllegalStateFilterException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class GenericFilter<T> {

    private final SpecificationFactory<T> specificationFactory;

    public <U extends GenericFilterParameters> GenericSpecificationsBuilder<T> filterBy(U filterParameters) {
        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();

        Field[] fields = filterParameters.getClass().getDeclaredFields();

        try {

            for (Field field : fields) {

                field.setAccessible(true);
                Object valueField = field.get(filterParameters);

                if (Objects.nonNull(valueField)) {
                    switch (field.getName()) {
                        case "authors" -> builder.with(specificationFactory.isEqual("authors", "fio", Arrays.asList((String[]) valueField)));
                        case "genres" -> builder.with(specificationFactory.isEqual("genre", "genre", Arrays.asList((String[]) valueField)));
                        case "from" -> builder.with(specificationFactory.greaterThanOrEqually("createdAt", Collections.singletonList(valueField)));
                        case "to" -> builder.with(specificationFactory.lessThanOrEqually("createdAt", Collections.singletonList(valueField)));
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
