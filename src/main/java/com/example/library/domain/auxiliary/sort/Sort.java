package com.example.library.domain.auxiliary.sort;

import com.example.library.domain.еnum.SortOrder;
import com.example.library.exception.NotSupportedTypeException;
import com.example.library.util.ReflectionUtil;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
public class Sort<T> implements Sortable<T>, Comparator<T> {

    private final String key;
    private final SortOrder order;
    private final String keyInnerEntity;

    public static <T> Sort<T> by(SortParameters sortParameters) {
        return new Sort<>(sortParameters.getKey(), sortParameters.getOrder(), sortParameters.getKeyInnerEntity());
    }

    public static <T> Sort<T> by(String key, String keyInnerEntity, SortOrder order) {
        return new Sort<>(key, order, keyInnerEntity);
    }

    public static <T> Sort<T> by(String key, SortOrder order) {
        return new Sort<>(key, order, null);
    }

    @Override
    public List<T> sort(List<T> items) {
        return Objects.isNull(keyInnerEntity) ? sortByKeyEntity(items) : sortByKeyInnerEntity(items);
    }

    @Override
    public int compare(Object item1, Object item2) {

        if (item1 instanceof String item1Str && item2 instanceof String item2Str) {
            return order.getSortOrder() * item1Str.compareToIgnoreCase(item2Str);
        } else if (item1 instanceof Number item1Num && item2 instanceof Number item2Num) {
            return order.getSortOrder() * Integer.compare(item1Num.intValue(), item2Num.intValue());
        } else if (item1 instanceof Instant date1 && item2 instanceof Instant date2) {
            return order.getSortOrder() * date1.compareTo(date2);
        }

        throw new NotSupportedTypeException("Error: data type only supported 'String', 'Number' or 'Instant'");
    }

    protected List<T> sortByKeyEntity(List<T> items) {
        return items.stream().sorted((item1, item2) -> {
            try {

                Object value1 = ReflectionUtil.retrieveFieldOfObject(item1, key);
                Object value2 = ReflectionUtil.retrieveFieldOfObject(item2, key);

                return compare(value1, value2);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return 0;

        }).toList();
    }

    protected List<T> sortByKeyInnerEntity(List<T> items) {
        return items.stream().sorted((item1, item2) -> {
            try {

                Object innerEntity1 = ReflectionUtil.retrieveFieldOfObject(item1, key);
                Object innerEntity2 = ReflectionUtil.retrieveFieldOfObject(item2, key);

                if (innerEntity1 instanceof List list1 && innerEntity2 instanceof List list2) {
                    return compareFirstElementsInLists(list1, list2);
                }

                Object valueFieldInnerEntity1 = ReflectionUtil.retrieveFieldOfObject(innerEntity1, keyInnerEntity);
                Object valueFieldInnerEntity2 = ReflectionUtil.retrieveFieldOfObject(innerEntity2, keyInnerEntity);

                return compare(valueFieldInnerEntity1, valueFieldInnerEntity2);

            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }

            return 0;

        }).toList();
    }

    private int compareFirstElementsInLists(List list1, List list2) throws NoSuchFieldException, IllegalAccessException {

        if (list1.isEmpty() || list2.isEmpty()) {

            if (list1.isEmpty() && list2.isEmpty()) {
                return 0;
            }

            return list1.size() == 0 ? -1 : 1;
        }

        Object valueFieldInnerEntity1 = ReflectionUtil.retrieveFieldOfObject(list1.get(0), keyInnerEntity);
        Object valueFieldInnerEntity2 = ReflectionUtil.retrieveFieldOfObject(list2.get(0), keyInnerEntity);

        return compare(valueFieldInnerEntity1, valueFieldInnerEntity2);
    }
}
