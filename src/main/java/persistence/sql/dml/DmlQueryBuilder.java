package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;

public class DmlQueryBuilder {

    public String buildInsertQuery(Object entity) {
        EntityMetadata metadata = EntityMetadata.from(entity.getClass());
        return "insert into " + metadata.getTableName() + " (" +
                String.join(", ", metadata.getInsertColumnNames()) +
                ") values (" +
                String.join(", ", metadata.extractInsertColumnValues(entity)) +
                ");";
    }

    public String buildSelectAllQuery(Class<?> entityClass) {
        EntityMetadata metadata = EntityMetadata.from(entityClass);

        return "select * from " + metadata.getTableName() + ";";
    }

    public String buildSelectByIdQuery(Class<?> entityClass, Object id) {
        EntityMetadata metadata = EntityMetadata.from(entityClass);

        return "select * from " + metadata.getTableName() + " where " + metadata.getPrimaryKeyName() + " = " + id + ";";
    }

    public String buildDeleteQuery(Object entity) {
        EntityMetadata metadata = EntityMetadata.from(entity.getClass());

        return "delete from " + metadata.getTableName() + " where " + metadata.getPrimaryKeyName() + " = " + metadata.extractPrimaryKeyValue(entity) + ";";
    }

}
