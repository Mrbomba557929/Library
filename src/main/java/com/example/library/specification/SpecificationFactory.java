package com.example.library.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SpecificationFactory<T> {

    public Specification<T> isEqual(String key, String keyInnerEntity, List<?> arguments) {
        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
        return builder.with(key, keyInnerEntity, SpecificationOperation.EQUALLY, arguments).build();
    }

    public Specification<T> like(String key, List<?> arguments) {
        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
        return builder.with(key, SpecificationOperation.LIKE, arguments).build();
    }

    public Specification<T> greaterThanOrEqually(String key, List<?> arguments) {
        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
        return builder.with(key, SpecificationOperation.GREATER_THAN_OR_EQUALLY, arguments).build();
    }

    public Specification<T> lessThanOrEqually(String key, List<?> arguments) {
        GenericSpecificationsBuilder<T> builder = new GenericSpecificationsBuilder<>();
        return builder.with(key, SpecificationOperation.LESS_THAN_OR_EQUALLY, arguments).build();
    }
}
