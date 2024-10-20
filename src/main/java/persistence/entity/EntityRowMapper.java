package persistence.entity;

import static persistence.validator.AnnotationValidator.notPredicate;

import jakarta.persistence.Transient;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;
import jdbc.RowMapper;
import persistence.sql.metadata.ColumnName;

public class EntityRowMapper<T> implements RowMapper<T> {

    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    /**
     * 기본 생성자로 인스턴스를 생성하고, ResultSet 값을 기반으로 Field 초기화
     */
    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            Object object = mapObject(clazz);
            mapFields(object, resultSet);
            return clazz.cast(object);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    private Object mapObject(Class<?> clazz)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Constructor<?> defaultConstructor = clazz.getConstructor();
        defaultConstructor.setAccessible(true);
        return defaultConstructor.newInstance();
    }

    private void mapFields(Object object, ResultSet resultSet) {
        Arrays.stream(object.getClass().getDeclaredFields())
                .filter(notPredicate(Transient.class))
                .forEach(field -> mapField(object, field, resultSet));
    }

    private void mapField(Object object, Field field, ResultSet resultSet) {
        try {
            field.setAccessible(true);
            field.set(object, resultSet.getObject(new ColumnName(field).value()));
        } catch (IllegalAccessException | SQLException ex) {
            throw new RuntimeException(ex);
        }
    }
}
