package persistence.meta;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import java.util.List;
import persistence.exception.NoEntityException;

public class EntityMeta {
    private final String tableName;
    private final EntityColumns entityColumns;

    public EntityMeta(Class<?> entityClass) {
        if (entityClass == null || entityClass.getAnnotation(Entity.class) == null) {
            throw new NoEntityException();
        }
        tableName = createTableName(entityClass);
        entityColumns = new EntityColumns(entityClass.getDeclaredFields());
    }

    private String createTableName(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Table.class) || entityClass.getAnnotation(Table.class).name().isBlank()) {
            return entityClass.getSimpleName();
        }
        return entityClass.getAnnotation(Table.class).name();
    }

    public String getTableName() {
        return tableName;
    }

    public List<EntityColumn> getEntityColumns() {
        return entityColumns.getEntityColumns();
    }
}
