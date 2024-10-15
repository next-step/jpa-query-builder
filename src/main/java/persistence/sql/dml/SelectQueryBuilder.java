package persistence.sql.dml;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class SelectQueryBuilder {
    public String findAll(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }

        String tableName = getTableName(entityClass);
        return "SELECT * FROM " + tableName + ";";
    }
    public String findById(Class<?> entityClass, Object id) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }

        String tableName = getTableName(entityClass);
        return "SELECT * FROM " + tableName + " WHERE id = " + id + ";";
    }

    private String getTableName(Class<?> field) {
        if (field.isAnnotationPresent(Table.class)) {
            Table table = field.getAnnotation(Table.class);
            return !table.name().isEmpty() ? table.name() : field.getSimpleName();
        }
        return field.getSimpleName();
    }
}
