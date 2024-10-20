package persistence.sql.dml;

import jakarta.persistence.*;


public class InsertQueryBuilder extends DMLQueryBuilder{

    public InsertQueryBuilder(Class<?> clazz) {
        super(clazz);
    }

    public String insert(Object entity) {
            Class<?> entityClass = entity.getClass();
            if (!entityClass.isAnnotationPresent(Entity.class)) {
                throw new IllegalArgumentException("This Class is not an Entity");
            }

            String tableName = getTableName();
            String columns = columnsClause();
            String values = valueClause(entity);

            return "INSERT INTO " + tableName + " (" + columns + ") VALUES (" + values + ");";
        }
}