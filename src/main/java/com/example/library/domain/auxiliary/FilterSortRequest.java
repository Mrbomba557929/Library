package com.example.library.domain.auxiliary;

import com.example.library.domain.auxiliary.filter.FilterParameters;
import com.example.library.domain.auxiliary.sort.SortParameters;
import lombok.Data;

@Data
public class FilterSortRequest {
    private FilterParameters filter;
    private SortParameters sort;
}
