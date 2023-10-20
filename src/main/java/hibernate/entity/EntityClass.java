package hibernate.entity;

import hibernate.entity.column.EntityColumn;
import hibernate.entity.column.EntityColumns;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.util.List;

public class EntityClass {

    private final String tableName;
    private final EntityColumns entityColumns;

    public EntityClass(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 어노테이션이 없는 클래스는 입력될 수 없습니다.");
        }
        this.tableName = parseTableName(clazz);
        this.entityColumns = new EntityColumns(clazz.getDeclaredFields());
    }

    private String parseTableName(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName();
        }
        String tableName = clazz.getAnnotation(Table.class).name();
        if (tableName.isEmpty()) {
            return clazz.getSimpleName();
        }
        return tableName;
    }

    public String tableName() {
        return tableName;
    }

    public List<EntityColumn> getEntityColumns() {
        return entityColumns.getValues();
    }
}
