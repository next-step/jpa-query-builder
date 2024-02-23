package persistence.sql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import jdbc.RowMapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class RowMapperFactory {
    private RowMapperFactory() {
    }

    public static <T> RowMapper<T> createRowMapper(Class<T> clazz) {
        return resultSet -> {
            final T instance = clazz.getDeclaredConstructor().newInstance();

            final List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                    .sorted(Comparator.comparing(RowMapperFactory::idFirstOrdered))
                    .filter(RowMapperFactory::isNotTransientField)
                    .collect(Collectors.toList());

            for (Field field : fields) {
                System.out.println(resultSet.getObject(getFieldName(field)));
                field.setAccessible(true);
                field.set(instance, resultSet.getObject(getFieldName(field)));
            }

            return instance;
        };
    }

    private static boolean isNotTransientField(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

    private static String getFieldName(final Field field) {
        if (isNotBlankOf(field)) {
            return field.getAnnotation(Column.class).name();
        }

        return field.getName();
    }

    private static boolean isNotBlankOf(final Field field) {
        return field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isBlank();
    }

    private static Integer idFirstOrdered(Field field) {
        return field.isAnnotationPresent(Id.class) ? 0 : 1;
    }
}
