package persistence.entity;

import jdbc.RowMapper;
import persistence.sql.Queryable;
import persistence.sql.definition.TableDefinition;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GenericRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public GenericRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        try {
            final TableDefinition tableDefinition = new TableDefinition(clazz);

            T instance = clazz.getDeclaredConstructor().newInstance();

            for (Queryable field : tableDefinition.queryableColumns()) {
                final String databaseColumnName = field.name();
                final String objectDeclaredName = field.declaredName();

                final Field declaredField = clazz.getDeclaredField(objectDeclaredName);
                final boolean wasAccessible = declaredField.canAccess(instance);
                final Object columnValue = resultSet.getObject(databaseColumnName);

                if (!wasAccessible) {
                    declaredField.setAccessible(true);
                }

                declaredField.set(instance, columnValue);

                if (!wasAccessible) {
                    declaredField.setAccessible(false);
                }
            }

            return instance;
        } catch (ReflectiveOperationException e) {
            throw new SQLException("Failed to map row to " + clazz.getName(), e);
        }
    }
}
