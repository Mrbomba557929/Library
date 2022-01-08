package com.example.library.specification;

import com.example.library.exception.SearchOperationNotSupportedException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import javax.persistence.criteria.*;
import java.util.Objects;

public record GenericSpecification<T>(SpecificationCriteria specificationCriteria) implements Specification<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        query.distinct(true);
        Expression<?> expression;

        if (Objects.nonNull(specificationCriteria.keyInnerEntity())) {
            Join<Object, Object> joinParent = root.join(specificationCriteria.key());
            expression = specificationCriteria.keyInnerEntity().equalsIgnoreCase("fio") ?
                    cb.concat(cb.concat(joinParent.get("firstName"), " "), joinParent.get("lastName")) :
                    joinParent.get(specificationCriteria.keyInnerEntity());
        } else {
            expression = root.get(specificationCriteria.key());
        }

        return cb.or(specificationCriteria.arguments()
                .stream()
                .map(arg -> getPredicate(expression, (Comparable<?>) arg, cb))
                .toArray(Predicate[]::new));
    }

    @SuppressWarnings("unchecked")
    private Predicate getPredicate(Expression expression, Comparable arg, CriteriaBuilder cb) {
        switch (specificationCriteria.operation()) {
            case EQUALLY -> {
                return cb.equal(expression, arg);
            }
            case GREATER_THAN_OR_EQUALLY -> {
                return cb.greaterThanOrEqualTo(expression, arg);
            }
            case LESS_THAN_OR_EQUALLY -> {
                return cb.lessThanOrEqualTo(expression, arg);
            }
            case LIKE -> {
                return cb.like(expression, (String) arg);
            }
            default -> throw ErrorFactory.exceptionBuilder(ErrorMessage.SEARCH_OPERATION_NOT_SUPPORTED)
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .build(SearchOperationNotSupportedException.class);
        }
    }
}
