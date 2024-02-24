package persistence.sql.dml.repository;

import jdbc.JdbcTemplate;
import persistence.sql.dml.exception.FieldSetValueException;
import persistence.sql.dml.exception.InstanceException;
import persistence.sql.dml.exception.InvalidFieldValueException;
import persistence.sql.dml.exception.NotFoundFieldException;
import persistence.sql.entity.EntityMappingTable;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.util.List;

public abstract class Repository<T> {

    protected final JdbcTemplate jdbcTemplate;
    protected final Class<T> clazz;

    public Repository(final JdbcTemplate jdbcTemplate,
                      final Class<T> clazz) {
        this.jdbcTemplate = jdbcTemplate;
        this.clazz = clazz;
    }

    abstract List<T> findAll(String sql);

    public T mapper(ResultSet resultSet) {
        EntityMappingTable entityMappingTable = EntityMappingTable.from(clazz);
        T instance = createInstance();

        entityMappingTable.getDomainTypeList()
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

}
