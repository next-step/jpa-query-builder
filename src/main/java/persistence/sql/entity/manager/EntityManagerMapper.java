package persistence.sql.entity.manager;

import persistence.sql.dml.exception.*;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainType;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.Spliterator;
import java.util.stream.StreamSupport;

public class EntityManagerMapper<T> {

    private final Class<T> clazz;

    public EntityManagerMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    public T mapper(ResultSet resultSet) {
        EntityMappingTable entityMappingTable = EntityMappingTable.from(clazz);
        T instance = createInstance();

        Spliterator<DomainType> spliterator = entityMappingTable.getDomainTypes().spliterator();
        StreamSupport.stream(spliterator, false)
                .forEach(domainType -> {
                    Field field = getField(clazz, domainType.getName());
                    setField(instance, field, getValue(resultSet, domainType.getColumnName()));
                });

        return instance;
    }

    private T createInstance() {
        try {
            return clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new InstanceException();
        }
    }

    private Field getField(Class<T> clazz, String name) {
        try {
            return clazz.getDeclaredField(name);
        } catch (Exception e) {
            throw new NotFoundFieldException();
        }
    }

    private Object getValue(ResultSet resultSet, String columnName) {
        try {
            return resultSet.getObject(columnName);
        } catch (Exception e) {
            throw new InvalidFieldValueException();
        }
    }

    private void setField(Object instance, Field field, Object value) {
        try {
            field.setAccessible(true);
            field.set(instance, value);
        } catch (Exception e) {
            throw new FieldSetValueException();
        }
    }

    public String getFieldValue(final T entity, final String columnName) {
        try {
            Field field = clazz.getDeclaredField(columnName);
            field.setAccessible(true);
            return field.get(entity).toString();
        } catch (Exception e) {
            throw new NotFoundIdException();
        }
    }
}
