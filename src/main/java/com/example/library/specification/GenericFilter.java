package com.example.library.specification;

import com.example.library.exception.IllegalStateFilterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.List;
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
                if (Objects.nonNull(field)) {

                    field.setAccessible(true);
                    Object valueField = field.get(filterParameters);

                    switch (field.getName()) {
                        case "authors" -> builder.with(specificationFactory.isEqual("author.firstName", (List<?>) valueField));
                        case "genres" -> builder.with(specificationFactory.isEqual("genre.genre", (List<?>) valueField));
                        case "from" -> builder.with(specificationFactory.greaterThanOrEqually("createdAt", Collections.singletonList(valueField)));
                        case "to" -> builder.with(specificationFactory.lessThanOrEqually("createdAt", Collections.singletonList(valueField)));
                    }
                }
            }

        } catch (IllegalStateException | IllegalAccessException e) {
            throw new IllegalStateFilterException("Error: Illegal state filter!");
        }

        return builder;
    }
}
