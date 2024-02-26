package jdbc;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public GenericRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(final ResultSet resultSet) throws SQLException, InstantiationException,
            IllegalAccessException, NoSuchMethodException, InvocationTargetException {

        T instance = clazz.getDeclaredConstructor().newInstance();

        final List<Field> fields = Arrays.stream(clazz.getDeclaredFields())
                .filter(this::isNotTransientAnnotationPresent)
                .collect(Collectors.toList());

        for (Field field : fields) {
            field.setAccessible(true);
            field.set(instance, resultSet.getObject(getFieldName(field)));
        }
        return instance;
    }

    private String getFieldName(final Field field) {
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).name().isBlank()) {
            return field.getAnnotation(Column.class).name();
        }

        return field.getName();
    }

    private boolean isNotTransientAnnotationPresent(final Field field) {
        return !field.isAnnotationPresent(Transient.class);
    }

}
