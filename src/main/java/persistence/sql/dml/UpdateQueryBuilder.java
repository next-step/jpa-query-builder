package persistence.sql.dml;

import jakarta.persistence.Entity;


public class UpdateQueryBuilder extends DMLQueryBuilder {

    public UpdateQueryBuilder(Class<?> clazz) {
        super(clazz);
    }

    public String update(Object entity) {
        Class<?> entityClass = entity.getClass();
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException("This Class is not an Entity");
        }

        String tableName = getTableName();
        String setClause = setClause(entity);
        String idClause = idClause(entity);

        if (idClause.isEmpty()) {
            throw new IllegalArgumentException("Entity must have an @Id field");
        }

        return "UPDATE " + tableName + " SET " + setClause + " WHERE " + idClause + ";";
    }
}