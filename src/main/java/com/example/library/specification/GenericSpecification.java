package com.example.library.specification;

import com.example.library.exception.SearchOperationNotSupportedException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RequiredArgsConstructor
public class GenericSpecification<T> implements Specification<T> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();
        query.distinct(true);

        if (Objects.nonNull(searchCriteria.getKeyInnerEntity())) {
            Join<Object, Object> joinParent = root.join(searchCriteria.getKey());
            searchCriteria.getArguments().forEach(arg ->
                    predicates.add(generatePredicate(searchCriteria.getKeyInnerEntity(), arg, joinParent, criteriaBuilder)));
        } else {
            searchCriteria.getArguments().forEach(arg ->
                    predicates.add(generatePredicate(searchCriteria.getKey(), arg, root, criteriaBuilder)));
        }

        return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
    }

    private Predicate generatePredicate(String key, Object arg, From<?, ?> root, CriteriaBuilder criteriaBuilder) {

        switch (searchCriteria.getOperation()) {
            case EQUALLY -> {
                return criteriaBuilder.equal(root.get(key), arg);
            }
            case GREATER_THAN_OR_EQUALLY -> {
                return criteriaBuilder.greaterThanOrEqualTo(root.get(key), (Comparable) arg);
            }
            case LESS_THAN_OR_EQUALLY -> {
                return criteriaBuilder.lessThanOrEqualTo(root.get(key), (Comparable) arg);
            }
        }

        throw ErrorFactory.exceptionBuilder(ErrorMessage.SEARCH_OPERATION_NOT_SUPPORTED)
                .status(HttpStatus.EXPECTATION_FAILED)
                .build(SearchOperationNotSupportedException.class);
    }
}
