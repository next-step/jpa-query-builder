package database.mapper;

import jdbc.RowMapper;
import utils.EntityAnnotationUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.List;

public class ResultMapper<T> implements RowMapper<T> {

    private Class<T> entityClass;

    public ResultMapper(final Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T mapRow(final ResultSet resultSet) {
        try {
            T entity = entityClass.getConstructor().newInstance();
            EntityAnnotationUtils.getNonTransientData(entityClass.getDeclaredFields()).forEach(field -> {
                try {
                    Class<?> type = field.getType();
                    field.setAccessible(true);
                    String columnName = EntityAnnotationUtils.parseColumnName(field);
                    if (type.equals(String.class)) {
                        String string = resultSet.getString(columnName);
                        field.set(entity, string);
                    }
                    if (type.equals(Integer.class)) {
                        field.set(entity, resultSet.getInt(columnName));
                    }
                    if (type.equals(Long.class)) {
                        field.set(entity, resultSet.getLong(columnName));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            return entity;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
