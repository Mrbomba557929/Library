package com.example.library.specification;

import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericSpecificationsBuilder<T> {

    private final List<SpecificationCriteria> params;
    private final List<Specification<T>> specifications;

    public GenericSpecificationsBuilder() {
        params = new ArrayList<>();
        specifications = new ArrayList<>();
    }

    public final GenericSpecificationsBuilder<T> with(String key, boolean isOrOperation, SpecificationOperation specificationOperation, List<?> arguments) {
        return with(key, null, isOrOperation, specificationOperation, arguments);
    }

    public final GenericSpecificationsBuilder<T> with(String key, String keyInnerEntity, boolean isOrOperation, SpecificationOperation specificationOperation, List<?> arguments) {
        SpecificationCriteria specificationCriteria = SpecificationCriteria.builder()
                .key(key)
                .keyInnerEntity(keyInnerEntity)
                .isOrOperation(isOrOperation)
                .operation(specificationOperation)
                .arguments(arguments)
                .build();
        params.add(specificationCriteria);
        return this;
    }

    public final GenericSpecificationsBuilder<T> with(Specification<T> specification) {
        specifications.add(specification);
        return this;
    }

    public Specification<T> build() {

        Specification<T> result = null;

        if (!params.isEmpty()) {
            result = new GenericSpecification<>(params.get(0));

            for (int i = 1; i < params.size(); i++) {
                SpecificationCriteria criteria = params.get(i);
                result = criteria.isOrOperation() ?
                        Specification.where(result).or(new GenericSpecification<>(criteria)) :
                        Specification.where(result).and(new GenericSpecification<>(criteria));
            }
        }

        if (!specifications.isEmpty()) {
            int index = 0;

            if (Objects.isNull(result)) {
                result = specifications.get(index++);
            }

            for (; index < specifications.size(); ++index) {
                result = Specification.where(result).and(specifications.get(index));
            }
        }

        return result;
    }
}
