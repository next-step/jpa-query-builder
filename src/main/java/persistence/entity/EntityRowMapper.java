package persistence.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import jdbc.RowMapper;
import persistence.sql.dialect.ColumnType;
import persistence.sql.schema.EntityClassMappingMeta;

public class EntityRowMapper<T> implements RowMapper<T> {

    private final Class<T> type;
    private final EntityClassMappingMeta entityClassMappingMeta;

    public EntityRowMapper(Class<T> type, ColumnType columnType) {
        this.type = type;
        this.entityClassMappingMeta = EntityClassMappingMeta.of(type, columnType);
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            final Object object = initDefaultObject();
            mapColumnToField(object, resultSet);
            return type.cast(object);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Object initDefaultObject() throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final Constructor<?> defaultConstructor = entityClassMappingMeta.getDefaultConstructor();
        defaultConstructor.setAccessible(true);
        return defaultConstructor.newInstance();
    }

    private void mapColumnToField(Object object, ResultSet resultSet) {
        entityClassMappingMeta.getMappingFieldList()
            .forEach(field -> trySetFieldValue(object, resultSet, field));
    }

    private void trySetFieldValue(Object object, ResultSet resultSet, Field field) {
        try {
            field.setAccessible(true);
            field.set(object, resultSet.getObject(entityClassMappingMeta.getMappingColumnName(field)));
        } catch (IllegalAccessException | SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
