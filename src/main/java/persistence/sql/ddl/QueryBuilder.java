package persistence.sql.ddl;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.lang.reflect.Field;
import persistence.exception.NoEntityException;

public class QueryBuilder<T> {
    protected final Class<T> entityClass;
    protected final Field[] entityFields;
    protected final String tableName;

    protected QueryBuilder(Class<T> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new NoEntityException();
        }
        this.entityClass = entityClass;
        this.entityFields = entityClass.getDeclaredFields();
        this.tableName = createTableName();
    }

    private String createTableName() {
        if (!entityClass.isAnnotationPresent(Table.class) || entityClass.getAnnotation(Table.class).name().isBlank()) {
            return entityClass.getSimpleName();
        }
        return entityClass.getAnnotation(Table.class).name();
    }

}
