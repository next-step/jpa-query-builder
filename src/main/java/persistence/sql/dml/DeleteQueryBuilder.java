package persistence.sql.dml;

import jakarta.persistence.Entity;

public class DeleteQueryBuilder extends DMLQueryBuilder {

    public DeleteQueryBuilder(Class<?> clazz) {
        super(clazz);
    }

    public String deleteById(Class<?> entityClass, Object id) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }

        String tableName = getTableName();
        return "DELETE FROM " + tableName + " WHERE id = " + id + ";";
    }
}
