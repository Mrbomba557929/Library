package com.example.library.specification;

import com.example.library.exception.SearchOperationNotSupportedException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import javax.persistence.criteria.*;

public record GenericSpecification<T>(SpecificationCriteria specificationCriteria) implements Specification<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        query.distinct(true);
        Expression<?> expression;

        switch (specificationCriteria.key()) {
            case "authors" -> {
                Join<Object, Object> joinParent = root.join(specificationCriteria.key());
                expression = cb.concat(cb.concat(joinParent.get("firstName"), " "), joinParent.get("lastName"));
            }
            case "genre" -> {
                Join<Object, Object> joinParent = root.join(specificationCriteria.key());
                expression = joinParent.get(specificationCriteria.keyInnerEntity());
            }
            case "createdAt" -> {
                expression = cb.function("YEAR", Integer.class, root.get(specificationCriteria.key()));
            }
            default -> {
                expression = root.get(specificationCriteria.key());
            }
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
