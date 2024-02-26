package persistence.sql.mapper;

import jdbc.RowMapper;
import persistence.sql.column.Columns;
import persistence.sql.column.GeneralColumn;
import persistence.sql.column.IdColumn;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;
    private final Dialect dialect;

    public GenericRowMapper(Class<T> clazz, Dialect dialect) {
        this.clazz = clazz;
        this.dialect = dialect;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        try {
            T instance = clazz.getDeclaredConstructor().newInstance();
            mapIdColumn(resultSet, instance);
            mapGeneralColumns(resultSet, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | NoSuchMethodException e) {
            throw new SQLException(e);
        } catch (InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private void mapGeneralColumns(ResultSet resultSet, T instance) throws SQLException, NoSuchFieldException, IllegalAccessException {
        Columns columns = new Columns(clazz.getDeclaredFields(), dialect);
        for (GeneralColumn column : columns.getValues()) {
            String fieldName = column.getFieldName();
            String columnName = column.getName();
            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(instance, resultSet.getObject(columnName));
        }
    }

    private void mapIdColumn(ResultSet resultSet, T instance) throws SQLException, NoSuchFieldException, IllegalAccessException {
        IdColumn idColumn = new IdColumn(clazz.getDeclaredFields(), dialect);
        String fieldName = idColumn.getFieldName();
        String idColumnName = idColumn.getName();

        Field idField = clazz.getDeclaredField(fieldName);
        idField.setAccessible(true);
        idField.set(instance, resultSet.getObject(idColumnName));
    }
}
