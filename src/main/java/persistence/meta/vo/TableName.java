package persistence.meta.vo;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class TableName {
    private final String tableName;
    public static TableName createFromClass(Class<?> cls) {
        if (!cls.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("Class is not annotated with @Entity");
        }
        String tableName = cls.getSimpleName();
        if (cls.isAnnotationPresent(Table.class)) {
            Table annotation = cls.getAnnotation(Table.class);
            if (annotation.name() != null && !annotation.name().isBlank()) {
                tableName = annotation.name();
            }
        }
        return new TableName(tableName);
    }

    private TableName(String tableName) {
        this.tableName = tableName;
    }


    public String getTableName() {
        return tableName;
    }
}
