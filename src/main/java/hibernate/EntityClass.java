package hibernate;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class EntityClass {

    private final String tableName;

    public EntityClass(final Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Entity 어노테이션이 없는 클래스는 입력될 수 없습니다.");
        }
        this.tableName = parseTableName(clazz);
    }

    private String parseTableName(Class<?> clazz) {
        if (clazz.isAnnotationPresent(Table.class)) {
            return clazz.getAnnotation(Table.class).name();
        }
        return clazz.getSimpleName();
    }

    public String tableName() {
        return tableName;
    }
}
