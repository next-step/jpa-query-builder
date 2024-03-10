package persistence.entity;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import jdbc.RowMapper;
import persistence.exception.ReflectionRuntimeException;
import persistence.sql.ddl.ColumnTranslator;

public class EntityRowMapperFactory {

    private EntityRowMapperFactory() {
        // Do nothing
    }

    public static class LazyLoadEntityRowMapperFactory {
        private static final EntityRowMapperFactory INSTANCE = new EntityRowMapperFactory();
    }

    public static EntityRowMapperFactory getInstance() {
        return LazyLoadEntityRowMapperFactory.INSTANCE;
    }

    public <T> RowMapper<T> getRowMapper(Class<T> entityClass) {
        return resultSet -> {
            try {
                ColumnTranslator columnTranslator = new ColumnTranslator();
                Constructor<T> declaredConstructor = entityClass.getDeclaredConstructor();
                declaredConstructor.setAccessible(true);
                T entity = entityClass.getDeclaredConstructor().newInstance();

                List<Field> columnFieldList = columnTranslator.getColumnFieldStream(entityClass)
                    .collect(Collectors.toList());

                for (Field field : columnFieldList) {
                    field.setAccessible(true);
                    String columnName = columnTranslator.getColumnNameFrom(field);
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
