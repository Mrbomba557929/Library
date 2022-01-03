package com.example.library.specification;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class GenericSpecification<T> implements Specification<T> {

    private final SearchCriteria searchCriteria;

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        for (Object arg : searchCriteria.getArguments()) {
            switch (searchCriteria.getOperation()) {
                case EQUALITY -> predicates.add(criteriaBuilder.equal(root.get(searchCriteria.getKey()), arg));
                case GREATER_THAN_OR_EQUALLY -> predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get(searchCriteria.getKey()), (Comparable) arg));
                case LESS_THAN_OR_EQUALLY -> predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get(searchCriteria.getKey()), (Comparable) arg));
            }
        }

        return criteriaBuilder.or(predicates.toArray(new Predicate[0]));
    }
}
