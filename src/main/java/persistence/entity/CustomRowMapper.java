package persistence.entity;

import jdbc.RowMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.meta.EntityField;
import persistence.sql.meta.EntityTable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;

public class CustomRowMapper<T> implements RowMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(CustomRowMapper.class);

    private final Class<T> clazz;

    public CustomRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        final T entity = new InstanceFactory<>(clazz).createInstance();
        new EntityTable(clazz).getEntityFields()
                .stream()
                .filter(EntityField::isPersistent)
                .forEach(entityField -> mapField(resultSet, entityField, entity));
        return entity;
    }

    private void mapField(ResultSet resultSet, EntityField entityField, T entity) {
        try {
            final Object value = getValue(resultSet, entityField);
            entityField.setValue(entity, value);

        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        }
    }

    private Object getValue(ResultSet resultSet, EntityField entityField) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        final Method getterMethod = resultSet.getClass()
                .getDeclaredMethod(entityField.getResultSetGetterName(), String.class);
        return getterMethod.invoke(resultSet, entityField.getColumnName());
    }
}
