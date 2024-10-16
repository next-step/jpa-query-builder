package persistence.sql.dml;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class SelectQueryBuilder extends DMLQueryBuilder {
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
}
