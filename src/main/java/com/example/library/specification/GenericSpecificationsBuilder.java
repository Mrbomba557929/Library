package com.example.library.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class GenericSpecificationsBuilder<T> {

    private List<SearchCriteria> params;
    private List<Specification<T>> specifications;

    public final GenericSpecificationsBuilder<T> with(String key, SearchOperation searchOperation, List<?> arguments) {
        params.add(new SearchCriteria(key, searchOperation,arguments));
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
                result = Specification.where(result).or(new GenericSpecification<>(params.get(i)));
            }
        }

        if (!specifications.isEmpty()) {
            int index = 0;

            if (Objects.isNull(result)) {
                result = specifications.get(index++);
            }

            for (; index < specifications.size(); ++index) {
                result = Specification.where(result).or(specifications.get(index));
            }
        }

        return result;
    }
}
