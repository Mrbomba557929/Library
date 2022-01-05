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

    private final SpecificationCriteria specificationCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();
        query.distinct(true);

        if (Objects.nonNull(specificationCriteria.getKeyInnerEntity())) {
            Join<Object, Object> joinParent = root.join(specificationCriteria.getKey());
            specificationCriteria.getArguments().forEach(arg ->
                    predicates.add(generatePredicate(specificationCriteria.getKeyInnerEntity(), arg, joinParent, criteriaBuilder)));
        } else {
            specificationCriteria.getArguments().forEach(arg ->
                    predicates.add(generatePredicate(specificationCriteria.getKey(), arg, root, criteriaBuilder)));
        }

        return criteriaBuilder.or(predicates.toArray(Predicate[]::new));
    }

    private Predicate generatePredicate(String key, Object arg, From<?, ?> root, CriteriaBuilder criteriaBuilder) {

        switch (specificationCriteria.getOperation()) {
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
