package com.example.library.domain.page;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Page<T> implements Pageable<T> {

    private final List<T> items;
    private final int numberOfPage;
    private final int currentPage;
    private final int numberOfElements;
    private final boolean hasPrevious;
    private final boolean hasNext;

    @Override
    public int getNumberOfPages() {
        return numberOfPage;
    }

    @Override
    public int getNumberOfElements() {
        return numberOfElements;
    }

    @Override
    public List<T> getContent() {
        return items;
    }

    @Override
    public boolean hasContent() {
        return !items.isEmpty();
    }

    @Override
    public boolean isFirst() {
        return !hasPrevious;
    }

    @Override
    public boolean isLast() {
        return !hasNext;
    }

    @Override
    public boolean hasNext() {
        return hasNext;
    }

    @Override
    public boolean hasPrevious() {
        return hasPrevious;
    }

    @Override
    public Iterator<T> iterator() {
        return new PageIterator();
    }

    @Override
    public <U> Pageable<U> map(Function<? super T, ? extends U> converter) {
        List<U> editedItems = items.stream().map(converter).collect(Collectors.toList());
        return new Page<>(editedItems, numberOfPage, currentPage, numberOfElements, hasPrevious, hasNext);
    }

    protected class PageIterator implements Iterator<T> {

        private int index;

        @Override
        public boolean hasNext() {
            return index < items.size();
        }

        @Override
        public T next() {

            if (this.hasNext()){
                return items.get(index++);
            }

            return null;
        }
    }
}
