package com.example.library.specification;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SpecificationCriteria {
    private String key;
    private String keyInnerEntity;
    private SpecificationOperation operation;
    private List<?> arguments;
}
