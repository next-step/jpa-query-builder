package persistence.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import jdbc.RowMapper;
import persistence.exception.ReflectionRuntimeException;
import persistence.sql.ddl.ColumnQueryTranslator;

public class EntityRowMapperFactory {

    private EntityRowMapperFactory() {
        // Do nothing
    }

    public static class CacheEntityRowMapperFactory {
        private CacheEntityRowMapperFactory() {
            // Do nothing
        }

        private static final EntityRowMapperFactory INSTANCE = new EntityRowMapperFactory();
    }

    public static EntityRowMapperFactory getInstance() {
        return CacheEntityRowMapperFactory.INSTANCE;
    }

    public <T> RowMapper<T> getRowMapper(Class<T> entityClass) {
        return resultSet -> {
            try {
                ColumnQueryTranslator columnQueryTranslator = new ColumnQueryTranslator();
                Constructor<T> declaredConstructor = entityClass.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                T entity = entityClass.getDeclaredConstructor().newInstance();

                List<Field> columnFieldList = columnQueryTranslator.getColumnFieldStream(entityClass)
                    .collect(Collectors.toList());

                for (Field field : columnFieldList) {
                    field.setAccessible(true);
                    String columnName = columnQueryTranslator.getColumnNameFrom(field);
                    field.set(entity, resultSet.getObject(columnName));
                }

                return entity;
            } catch (InstantiationException | IllegalAccessException | NoSuchMethodException |
                     InvocationTargetException e) {
                throw new ReflectionRuntimeException(entityClass, e);
            }
        };
    }
}
