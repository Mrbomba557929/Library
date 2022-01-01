package com.example.library.domain.auxiliary.sort;

import java.util.List;

public interface Sortable <T> {
    List<T> sort(List<T> items);
}
