package persistence.sql.ddl.component;

import jakarta.persistence.Table;

public class EntityName {

    public String getTableName(Class<?> entityClass) {
        if (entityClass.isAnnotationPresent(Table.class)) {
            return getTableNameFromTableAnnotation(entityClass);
        }
        return getTableNameFromClassName(entityClass);
    }

    private static String getTableNameFromClassName(Class<?> entityClass) {
        return entityClass.getSimpleName().toLowerCase();
    }

    private static String getTableNameFromTableAnnotation(Class<?> entityClass) {
        String tableName = entityClass.getAnnotation(Table.class).name();
        if (tableName == null || tableName.isBlank()) {
            return getTableNameFromClassName(entityClass);
        }
        return tableName;
    }

}
