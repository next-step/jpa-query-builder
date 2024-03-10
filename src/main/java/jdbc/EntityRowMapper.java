package jdbc;

import jakarta.persistence.Transient;
import persistence.sql.metadata.ColumnMetadata;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

public class EntityRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            final List<Field> fields = getFields();
            return convertToInstance(resultSet, fields);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Field> getFields() {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .toList();
    }

    private T convertToInstance(ResultSet resultSet, List<Field> fields) {
        T instance;

        try {
            instance = clazz.getDeclaredConstructor().newInstance();
            for (Field field : fields) {
                field.setAccessible(true);
                ColumnMetadata columnMetadata = ColumnMetadata.of(field);
                field.set(instance, resultSet.getObject(columnMetadata.getName()));
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException |
                 SQLException e) {
            throw new RuntimeException(e);
        }

        return instance;
    }
}
