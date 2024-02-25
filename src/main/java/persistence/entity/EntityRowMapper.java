package persistence.entity;

import jakarta.persistence.Entity;
import jdbc.RowMapper;
import persistence.ReflectionUtils;
import persistence.sql.QueryException;
import persistence.sql.mapping.ColumnBinder;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;

public class EntityRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    private ResultSetMetaData metaData;

    private int columnCount = -1;

    final LinkedHashMap<String, Field> fields = new LinkedHashMap<>();

    public EntityRowMapper(Class<T> clazz) {
        final boolean isEntity = clazz.isAnnotationPresent(Entity.class);
        if (!isEntity) {
            throw new QueryException(clazz.getName() + " is not entity");
        }
        this.clazz = clazz;
    }

    @Override
    public T mapRow(final ResultSet resultSet) throws SQLException {
        T entity;

        if (metaData == null) {
            metaData = resultSet.getMetaData();
            columnCount = metaData.getColumnCount();
        }

        entity = ReflectionUtils.createInstance(clazz);
        extractFields(clazz);

        for (int i = 1; i <= columnCount; i++) {
            final String columnLabel = metaData.getColumnLabel(i);

            final Field field = getFieldByColumnName(columnLabel);
            final Object value = resultSet.getObject(columnLabel);

            ReflectionUtils.setFieldValue(field, entity, value);
        }

        return entity;
    }

    private void extractFields(final Class<T> clazz) {
        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> fields.put(ColumnBinder.toColumnName(field), field));
    }

    private Field getFieldByColumnName(final String columnName) {
        return this.fields.get(columnName.toLowerCase());
    }

}
