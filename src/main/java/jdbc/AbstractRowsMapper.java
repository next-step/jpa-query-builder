package jdbc;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import persistence.exception.NotFoundException;
import persistence.exception.QueryException;
import persistence.meta.ColumnType;
import persistence.meta.EntityColumn;
import persistence.meta.EntityMeta;

public  abstract class AbstractRowsMapper<T> implements RowMapper<T> {
    protected final Class<T> tClass;

    public AbstractRowsMapper(Class<T> tClass) {
        this.tClass = tClass;
    }

    protected T mapEntity(Class<T> tClass, ResultSet resultSet) {
        final T instance = getInstance(tClass);
        final EntityMeta entityMeta = new EntityMeta(instance.getClass());

        for (EntityColumn entityColumn : entityMeta.getEntityColumns()) {
            final Object resultSetColumn = getResultSetColumn(resultSet, entityColumn);
            final Field field = getFiled(tClass, entityColumn);
            setFieldValue(instance, field, resultSetColumn);
        }
        return instance;
    }

    private void setFieldValue(T instance, Field field, Object resultSetColumn) {
        try {
            field.setAccessible(true);
            field.set(instance, resultSetColumn);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private T getInstance(Class<T> tClass) {
        try {
            return tClass.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | InvocationTargetException | NoSuchMethodException | IllegalAccessException e) {
            throw new NotFoundException(e);
        }
    }

    private Field getFiled(Class<T> tClass, EntityColumn entityColumn) {
        try {
            return tClass.getDeclaredField(entityColumn.getFieldName());
        } catch (NoSuchFieldException e) {
            throw new NotFoundException("필드를 찾을수 없습니다.");
        }

    }

    private Object getResultSetColumn(ResultSet resultSet, EntityColumn column) {
        try {
            return getTypeValue(resultSet, column);
        } catch (SQLException e) {
            throw new QueryException(e);
        }
    }

    private static Object getTypeValue(ResultSet resultSet, EntityColumn column) throws SQLException {
        final ColumnType columType = column.getColumType();
        if (columType.isBigInt()) {
            return resultSet.getLong(column.getName());
        }
        if (columType.isVarchar()) {
            return resultSet.getString(column.getName());
        }
        if (columType.isInteger()) {
            return resultSet.getInt(column.getName());
        }
        return null;
    }

}
