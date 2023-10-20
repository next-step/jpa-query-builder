package jdbc;

import jakarta.persistence.Transient;
import persistence.sql.common.ColumnName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Objects;

public class ResultMapper<T> implements RowMapper<T> {
    private Class<T> tClass;

    public ResultMapper(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        T clazz = getConstructor();

        Arrays.stream(tClass.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .forEach(field -> {
                field.setAccessible(true);
                setFieldData(resultSet, field, clazz);
            });

        return clazz;
    }

    /**
     * resultSet의 타입을 객체 타입에 맞춰 가져옵니다.
     */
    private <R> R extracted(ResultSet resultSet, Field field, Class<R> rClass, String columnName) {
        try {
            if (field.getType().equals(Long.class)) {
                return rClass.cast(resultSet.getLong(columnName));
            } else if (field.getType().equals(Boolean.class)) {
                return rClass.cast(resultSet.getBoolean(columnName));
            } else if (field.getType().equals(Integer.class)) {
                return rClass.cast(resultSet.getInt(columnName));
            } else if (field.getType().equals(String.class)) {
                return rClass.cast(resultSet.getString(columnName));
            } else if (field.getType().equals(Double.class)) {
                return rClass.cast(resultSet.getDouble(columnName));
            } else if (field.getType().equals(Float.class)) {
                return rClass.cast(resultSet.getFloat(columnName));
            } else if (field.getType().equals(Short.class)) {
                return rClass.cast(resultSet.getShort(columnName));
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private T getConstructor() {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private void setFieldData(ResultSet resultSet, Field field, T clazz) {
        try {
            field.set(clazz, extracted(resultSet, field, field.getType(), Objects.requireNonNull(
                    ColumnName.of(field)).value()));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
