package persistence.sql.ddl.schema;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import persistence.sql.ddl.exception.RequiredAnnotationException;

public class TableMeta {

    private final String tableName;

    private TableMeta(String tableName) {
        this.tableName = tableName;
    }

    public static TableMeta of(Class<?> entityClazz) {
        validateEntityAnnotationIsPresent(entityClazz);

        if (entityClazz.isAnnotationPresent(Table.class)) {
            final Table tableAnnotation = entityClazz.getAnnotation(Table.class);
            return new TableMeta(tableAnnotation.name().toUpperCase());

        }

        return new TableMeta(entityClazz.getSimpleName().toUpperCase());
    }

    public String getTableName() {
        return tableName;
    }

    private static void validateEntityAnnotationIsPresent(Class<?> entityClazz) {
        if (entityClazz.isAnnotationPresent(Entity.class) == Boolean.FALSE) {
            throw new RequiredAnnotationException("@Entity annotation is required");
        }
    }
}
