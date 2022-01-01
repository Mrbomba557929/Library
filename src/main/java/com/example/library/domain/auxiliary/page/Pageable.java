package com.example.library.domain.auxiliary.page;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

public interface Pageable<T> {
    int getNumberOfPages();
    int getCurrentPage();
    int getNumberOfElements();
    List<T> getContent();
    boolean hasContent();
    boolean isFirst();
    boolean isLast();
    boolean hasNext();
    boolean hasPrevious();
    Iterator<T> iterator();
    <U> Pageable<U> map(Function<? super T, ? extends U> converter);
}
