package jdbc;

import jakarta.persistence.Transient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import persistence.sql.NameUtils;

import java.lang.reflect.Field;
import java.sql.ResultSet;

public class EntityRowMapper<T> implements RowMapper<T> {
    private static final Logger logger = LoggerFactory.getLogger(EntityRowMapper.class);

    private final Class<T> entityClass;

    public EntityRowMapper(Class<T> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public T mapRow(ResultSet resultSet) {
        try {
            T entity = this.entityClass.getDeclaredConstructor().newInstance();

            for (Field field : this.entityClass.getDeclaredFields()) {
                if (field.isAnnotationPresent(Transient.class)) {
                    continue;
                }
                field.setAccessible(true);
                Object object = resultSet.getObject(NameUtils.getColumnName(field), field.getType());
                field.set(entity, object);
            }
            return entity;
        } catch (Exception e) {
            logger.error("Error while mapping result set!");
            throw new RuntimeException(e);
        }
    }
}
