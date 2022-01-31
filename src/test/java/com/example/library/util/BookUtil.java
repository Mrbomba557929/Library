package com.example.library.util;

import com.example.library.domain.model.Book;
import com.example.library.exception.business.SortingException;
import com.example.library.exception.factory.ErrorFactory;
import com.google.common.base.Strings;
import org.springframework.data.domain.Sort;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;

import static com.example.library.exception.factory.ErrorMessage.SORTING_EXCEPTION;
import static org.springframework.http.HttpStatus.EXPECTATION_FAILED;

public class BookUtil implements Comparator<String> {

    @Override
    public int compare(String item1, String item2) {
        return item1.compareToIgnoreCase(item2);
    }

    public List<Book> sortByFirstElementOfCollection(List<Book> objects, Sort.Direction direction, String collection, String fieldOfElement) {

        if (Strings.isNullOrEmpty(collection) || Strings.isNullOrEmpty(fieldOfElement)) {
            return objects;
        }

        int numericDirection = direction == Sort.Direction.ASC ? 1 : -1;

        return objects.stream().sorted((item1, item2) -> {
            try {

                List<?> list1 = (List<?>) getValueField(item1, collection);
                List<?> list2 = (List<?>) getValueField(item2, collection);

                if (list1.isEmpty() && list2.isEmpty()) {
                    return 0;
                }

                if (list1.isEmpty() || list2.isEmpty()) {
                    return numericDirection * (list1.isEmpty() ? -1 : 1);
                }

                String firstName1 = (String) getValueField(list1.get(0), fieldOfElement);
                String firstName2 = (String) getValueField(list2.get(0), fieldOfElement);

                return numericDirection * compare(firstName1, firstName2);

            } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
                throw ErrorFactory.exceptionBuilder(SORTING_EXCEPTION)
                        .status(EXPECTATION_FAILED)
                        .build(SortingException.class);
            }
        }).toList();
    }

    private Object getValueField(Object object, String field) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field clazzField = clazz.getDeclaredField(field);
        clazzField.setAccessible(true);
        return clazzField.get(object);
    }
}
