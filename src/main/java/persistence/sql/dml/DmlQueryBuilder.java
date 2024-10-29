package persistence.sql.dml;

import persistence.sql.metadata.EntityMetadata;

public class DmlQueryBuilder<T> {

    private final EntityMetadata<T> entityMetadata;

    private DmlQueryBuilder(EntityMetadata<T> entityMetadata) {
        this.entityMetadata = entityMetadata;
    }

    public static <T> DmlQueryBuilder<T> from(Class<T> entityClass) {
        return new DmlQueryBuilder<>(EntityMetadata.from(entityClass));
    }

    public String buildInsertQuery(T entity) {
        return "insert into " + entityMetadata.getTableName() + " (" +
                String.join(", ", entityMetadata.getInsertColumnNames()) +
                ") values (" +
                String.join(", ", entityMetadata.getInsertColumnValues(entity)) +
                ");";
    }

    public String buildSelectAllQuery() {
        return "select * from " + entityMetadata.getTableName() + ";";
    }

    public String buildSelectByIdQuery(Object id) {
        return "select * from " + entityMetadata.getTableName() + " where " + entityMetadata.getPrimaryKeyName() + " = " + id + ";";
    }

    public String buildDeleteByIdQuery(Object id) {
        return "delete from " + entityMetadata.getTableName() + " where " + entityMetadata.getPrimaryKeyName() + " = " + id + ";";
    }

}
