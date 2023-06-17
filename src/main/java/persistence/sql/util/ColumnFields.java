package persistence.sql.util;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class ColumnFields {
    private ColumnFields() {}

    public static List<Field> forQuery(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(
                        field -> !field.isAnnotationPresent(Transient.class)
                ).collect(Collectors.toList());
    }

    public static List<Field> forInsert(Class<?> clazz) {
        return forQuery(clazz).stream()
                .filter(
                        field -> !field.isAnnotationPresent(Id.class)
                ).collect(Collectors.toList());
    }
}
