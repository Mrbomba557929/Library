package com.example.library.sort;

import com.example.library.exception.factory.ErrorFactory;
import com.example.library.exception.еnum.ErrorMessage;
import com.example.library.exception.SortingException;
import com.google.common.base.Strings;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomSort<T> implements Comparator<String> {

    @Override
    public int compare(String item1, String item2) {
        return item1.compareToIgnoreCase(item2);
    }

    public Page<T> sort(Page<T> page, Sort.Direction direction, String key, String innerKey) {

        if (Strings.isNullOrEmpty(key) || Strings.isNullOrEmpty(innerKey)) {
            return page;
        }

        int numericDirection = direction == Sort.Direction.ASC ? 1 : -1;

        List<T> sortedList = page.stream().sorted((item1, item2) -> {

            try {

                List<?> list1 = (List<?>) getValueField(item1, key);
                List<?> list2 = (List<?>) getValueField(item2, key);

                if (list1.isEmpty() && list2.isEmpty()) {
                    return 0;
                }

                if (list1.isEmpty() || list2.isEmpty()) {
                    return numericDirection * (list1.isEmpty() ? -1 : 1);
                }

                String firstName1 = (String) getValueField(list1.get(0), innerKey);
                String firstName2 = (String) getValueField(list2.get(0), innerKey);

                return numericDirection * compare(firstName1, firstName2);

            } catch (NoSuchFieldException | IllegalAccessException | ClassCastException e) {
                throw ErrorFactory.exceptionBuilder(ErrorMessage.SORTING_EXCEPTION)
                        .status(HttpStatus.EXPECTATION_FAILED)
                        .build(SortingException.class);
            }

        }).collect(Collectors.toList());

        return new PageImpl<>(sortedList);
    }

    private Object getValueField(Object object, String field) throws NoSuchFieldException, IllegalAccessException {
        Class<?> clazz = object.getClass();
        Field clazzField = clazz.getDeclaredField(field);
        clazzField.setAccessible(true);
        return clazzField.get(object);
    }
}

