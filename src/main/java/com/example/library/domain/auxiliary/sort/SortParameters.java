package com.example.library.domain.auxiliary.sort;

import com.example.library.domain.еnum.SortOrder;
import lombok.Data;

import java.util.Objects;

@Data
public class SortParameters {
    private String key;
    private String keyInnerEntity;
    private SortOrder order;

    public boolean isEmpty() {
        return Objects.isNull(key) &&
               Objects.isNull(keyInnerEntity) &&
               Objects.isNull(order);
    }
}
