package com.example.library.specification;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class SpecificationCriteria {
    private String key;
    private String keyInnerEntity;
    private boolean isOrOperation;
    private SpecificationOperation operation;
    private List<?> arguments;
}
