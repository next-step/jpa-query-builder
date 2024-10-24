package persistence.sql.entity;

import jdbc.RowMapper;

import jakarta.persistence.Column;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GenericRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;
    private final Map<String, Field> fieldsMap;
    private final Constructor<T> constructor;

    GenericRowMapper(final Class<T> clazz) {
        this.clazz = clazz;
        this.fieldsMap = new HashMap<>();

        try {
            this.constructor = clazz.getDeclaredConstructor();
            this.constructor.setAccessible(true);

            for (final Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                final Column column = field.getAnnotation(Column.class);
                final String columnName = column != null ? column.name() : field.getName();
                fieldsMap.put(columnName.toLowerCase(), field);
                fieldsMap.put(field.getName().toLowerCase(), field);
            }
        } catch (final NoSuchMethodException e) {
            throw new IllegalArgumentException("Class " + clazz.getName() + " must have a no-args constructor", e);
        }
    }

    @Override
    public T mapRow(final ResultSet resultSet) throws SQLException {
        try {
            final T instance = constructor.newInstance();
            final ResultSetMetaData metaData = resultSet.getMetaData();

            for (int i = 1; i <= metaData.getColumnCount(); i++) {
                final String columnName = metaData.getColumnLabel(i).toLowerCase();
                final Field field = fieldsMap.get(columnName);

                if (field != null) {
                    final Object value = getValueByFieldType(resultSet, i, field.getType());
                    field.set(instance, value);
                }
            }

            return instance;
        } catch (final ReflectiveOperationException e) {
            throw new SQLException("Error mapping row to class " + clazz.getName(), e);
        }
    }

    private Object getValueByFieldType(final ResultSet resultSet, final int index, final Class<?> fieldType) throws SQLException {
        if (resultSet.getObject(index) == null) {
            return null;
        }

        if (fieldType.equals(String.class)) {
            return resultSet.getString(index);
        } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
            return resultSet.getLong(index);
        } else if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
            return resultSet.getInt(index);
        } else if (fieldType.equals(Double.class) || fieldType.equals(double.class)) {
            return resultSet.getDouble(index);
        } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
            return resultSet.getBoolean(index);
        } else if (fieldType.equals(LocalDateTime.class)) {
            final Timestamp timestamp = resultSet.getTimestamp(index);
            return timestamp != null ? timestamp.toLocalDateTime() : null;
        } else if (fieldType.equals(Timestamp.class)) {
            return resultSet.getTimestamp(index);
        }
        return resultSet.getObject(index);
    }
}
