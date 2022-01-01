package com.example.library.domain.sort;

import com.example.library.domain.еnum.SortOrder;
import lombok.Data;

@Data
public class SortParameters {
    private String key;
    private String keyInnerEntity;
    private SortOrder order;
}
