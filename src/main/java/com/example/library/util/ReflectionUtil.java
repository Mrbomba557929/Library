package com.example.library.util;

import java.lang.reflect.Field;

public abstract class ReflectionUtil {

    public static Object retrieveFieldOfObject(Object object, String nameOfField) throws NoSuchFieldException, IllegalAccessException {
        Field field = object.getClass().getDeclaredField(nameOfField);
        field.setAccessible(true);
        return field.get(object);
    }
}
