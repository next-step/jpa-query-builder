package persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import jdbc.RowMapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityRowMapper<T> implements RowMapper<T> {
    private final Class<T> clazz;

    public EntityRowMapper(Class<T> clazz) {
        this.clazz = clazz;
    }

    @Override
    public T mapRow(ResultSet resultSet) throws SQLException {
        try {
            Constructor<T> constructor = clazz.getDeclaredConstructor();
            constructor.setAccessible(true);
            T entity = constructor.newInstance();
            System.out.println("Mapping row to entity: " + clazz.getSimpleName());
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (isTransient(field)) {
                    continue;
                }
                Object value = resultSet.getObject(getColumnName(field));
                System.out.println(value);
                field.set(entity, value);
            }

            return entity;
        } catch (Exception e) {
            throw new RuntimeException("Failed to map row to entity", e);
        }
    }

    private boolean isTransient(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private String getColumnName(Field field) {

        // Column 어노테이션이 존재하고 name 속성이 있으면 사용, 없으면 필드 이름 사용
        if (field.isAnnotationPresent(Column.class)) {
            Column column = field.getAnnotation(Column.class);
            if (!column.name().isEmpty()) {
                return column.name();
            }
        }
        return field.getName();
    }
}