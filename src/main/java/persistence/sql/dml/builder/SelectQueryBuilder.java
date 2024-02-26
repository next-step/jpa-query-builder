package persistence.sql.dml.builder;

import jakarta.persistence.Transient;
import jdbc.RowMapper;
import persistence.sql.dml.clause.Select;
import persistence.sql.dml.factory.SelectFactory;
import persistence.sql.meta.column.ColumnName;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectQueryBuilder {
    public SelectQueryDto<?> findAll(Class<?> clazz) {
        String sql = generateFindAllQuery(clazz);
        RowMapper<?> rowMapper = generateRowMapper(clazz);
        return new SelectQueryDto<>(sql, rowMapper);
    }

    public SelectQueryDto<?> findById(Class<?> clazz, Long id) {
        Select select = SelectFactory.getSelect(clazz);
        StringBuilder sb = new StringBuilder();
        sb.append(generateFindAllQuery(clazz));
        sb.append(" where ");
        sb.append(select.getPKName());
        sb.append(" = ");
        sb.append(id.toString());
        RowMapper<?> rowMapper = generateRowMapper(clazz);
        return new SelectQueryDto<>(sb.toString(), rowMapper);
    }

    private String generateFindAllQuery(Class<?> clazz) {
        Select select = SelectFactory.getSelect(clazz);
        return String.format("select %s from %s",
                select.getColumns(),
                select.getTableName()
        );
    }

    private RowMapper<?> generateRowMapper(Class<?> clazz) {
        return resultSet -> {
            Object result;
            try {
                result = clazz.getDeclaredConstructor().newInstance();
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                     NoSuchMethodException e) {
                throw new RuntimeException(e);
            }
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields) {
                if (field.isAnnotationPresent(Transient.class)) {
                    continue;
                }
                field.setAccessible(true);
                setValue(result, field, resultSet);
            }
            return result;
        };
    }

    private void setValue(Object object, Field field, ResultSet resultSet) throws SQLException {
        try {
            String columnName = new ColumnName(field).getName();
            if (field.getType().equals(Long.class)) {
                field.set(object, resultSet.getLong(columnName));
            }
            if (field.getType().equals(Integer.class)) {
                field.set(object, resultSet.getInt(columnName));
            }
            if (field.getType().equals(String.class)) {
                field.set(object, resultSet.getString(columnName));
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
