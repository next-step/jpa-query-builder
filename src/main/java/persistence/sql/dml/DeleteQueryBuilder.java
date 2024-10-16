package persistence.sql.dml;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

public class DeleteQueryBuilder extends DMLQueryBuilder {

    public String deleteById(Class<?> entityClass, Object id) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity ");
        }

        String tableName = getTableName(entityClass);
        return "DELETE FROM " + tableName + " WHERE id = " + id + ";";
    }
}
