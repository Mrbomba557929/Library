package com.example.library.domain.еnum;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SortOrder {
    ASC(-1),
    DESC(1);

    private final int sortOrder;

    @JsonCreator
    public static SortOrder fromString(String value) {

        for (SortOrder sortOrder : SortOrder.values()) {
            if (sortOrder.name().equalsIgnoreCase(value)) {
                return sortOrder;
            }
        }

        throw new IllegalArgumentException("Error: enter correct sort order! 'ASC' or 'DESC'");
    }
}
