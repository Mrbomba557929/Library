package com.example.library.specification;

import com.example.library.exception.SearchOperationNotSupportedException;
import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public record GenericSpecification<T>(SpecificationCriteria specificationCriteria) implements Specification<T> {

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

        List<Predicate> predicates = new ArrayList<>();
        query.distinct(true);

        Expression<?> expression;

        if (Objects.nonNull(specificationCriteria.getKeyInnerEntity())) {

            Join<Object, Object> joinParent = root.join(specificationCriteria.getKey());

            if (specificationCriteria.getKeyInnerEntity().equalsIgnoreCase("fio")) {
                expression = cb.concat(cb.concat(joinParent.get("firstName"), " "), joinParent.get("lastName"));
            } else {
                expression = joinParent.get(specificationCriteria.getKeyInnerEntity());
            }

        } else {
            expression = root.get(specificationCriteria.getKey());
        }

        for (Object arg : specificationCriteria.getArguments()) {
            predicates.add(getPredicate(expression, (Comparable<?>) arg, cb));
        }

        return cb.or(predicates.toArray(Predicate[]::new));
    }

    private Predicate getPredicate(Expression path, Comparable arg, CriteriaBuilder cb) {
        switch (specificationCriteria.getOperation()) {
            case EQUALLY -> {
                return cb.equal(path, arg);
            }
            case GREATER_THAN_OR_EQUALLY -> {
                return cb.greaterThanOrEqualTo(path, arg);
            }
            case LESS_THAN_OR_EQUALLY -> {
                return cb.lessThanOrEqualTo(path, arg);
            }
            case LIKE -> {
                return cb.like(path, (String) arg);
            }
            default -> throw ErrorFactory.exceptionBuilder(ErrorMessage.SEARCH_OPERATION_NOT_SUPPORTED)
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .build(SearchOperationNotSupportedException.class);
        }
    }
}
