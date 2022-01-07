package com.example.library.specification;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;

public record SpecificationCriteria(String key, String keyInnerEntity, boolean isOrOperation,
                                    List<?> arguments,
                                    SpecificationOperation operation) {
    @Builder
    @JsonCreator
    public SpecificationCriteria(@JsonProperty("key") String key, @JsonProperty("keyInnerEntity") String keyInnerEntity,
                                 @JsonProperty("isOrOperation") boolean isOrOperation, @JsonProperty("arguments") List<?> arguments,
                                 @JsonProperty("operation") SpecificationOperation operation) {
        this.key = key;
        this.keyInnerEntity = keyInnerEntity;
        this.isOrOperation = isOrOperation;
        this.arguments = arguments;
        this.operation = operation;
    }
}
