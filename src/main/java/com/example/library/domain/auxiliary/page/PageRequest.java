package com.example.library.domain.auxiliary.page;

import com.example.library.domain.auxiliary.sort.Sortable;
import com.google.common.collect.Lists;
import com.google.common.math.IntMath;

import java.math.RoundingMode;
import java.util.List;

public class PageRequest<E> {

    private final int numberOfPages;
    private final int page;
    private final int size;
    private final List<E> items;

    public PageRequest(int page, int size, List<E> items) {
        this.numberOfPages = IntMath.divide(items.size(), size, RoundingMode.CEILING);
        this.page = page < 0 ? 0 : Math.min(page, numberOfPages - 1);
        this.size = size < 0 ? items.size() : Math.min(size, items.size());
        this.items = items;
    }

    public static <T> Pageable<T> of(int page, int size, List<T> entities) {
        PageRequest<T> pageRequest = new PageRequest<>(page, size, entities);
        return pageRequest.doPage();
    }

    public static <T> Pageable<T> of(int page, int size, List<T> entities, Sortable<T> sort) {
        PageRequest<T> pageRequest = new PageRequest<>(page, size, sort.sort(entities));
        return pageRequest.doPage();
    }

    public Pageable<E> doPage() {
        List<E> currentPageElements = Lists.partition(items, size).get(page);
        boolean hasPrevious = page > 0;
        boolean hasNext = page < items.size() - 1;
        return new Page<>(currentPageElements, numberOfPages, page, items.size(), hasPrevious, hasNext);
    }
}
