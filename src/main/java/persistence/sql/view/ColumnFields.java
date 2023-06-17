package persistence.sql.view;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ColumnFields {
    private final List<Field> fields;

    private ColumnFields(List<Field> fields) {
        this.fields = fields;
    }

    public static ColumnFields forInsert(Class<?> clazz) {
        final List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class)
                        && !field.isAnnotationPresent(Id.class)
                ).collect(Collectors.toList());
        return new ColumnFields(fields);
    }

    public static ColumnFields from(Class<?> clazz) {
        final List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(
                        field -> !field.isAnnotationPresent(Transient.class)
                ).collect(Collectors.toList());
        return new ColumnFields(fields);
    }

    public Stream<Field> stream() {
        return fields.stream();
    }
}
