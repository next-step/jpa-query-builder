package jdbc;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public GenericRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(final ResultSet resultSet) {
        try {
            final List<Field> fields = getFields();
            return toInstance(resultSet, fields);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Field> getFields() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransientAnnotationPresent)
                .collect(Collectors.toList());
    }

    private String getFieldName(final Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isBlank()) {
            return field.getAnnotation(Column.class).name();
        }

        return field.getName();
    }

    private T toInstance(ResultSet resultSet, List<Field> fields) throws Exception {
        T instance = clazz.getDeclaredConstructor().newInstance();
        for (Field field : fields) {
            field.setAccessible(true);
            field.set(instance, resultSet.getObject(getFieldName(field)));
        }
        return instance;
    }

    private boolean isNotTransientAnnotationPresent(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

}
