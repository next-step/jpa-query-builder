package persistence.sql.dml;

import persistence.sql.ddl.metadata.EntityMetadata;

public class DmlQueryBuilder<T> {

    private final EntityMetadata entityMetadata;

    private DmlQueryBuilder(EntityMetadata entityMetadata) {
        this.entityMetadata = entityMetadata;
    }

    public static <T> DmlQueryBuilder<T> from(Class<T> entityClass) {
        return new DmlQueryBuilder<>(EntityMetadata.from(entityClass));
    }

    public String buildInsertQuery(T entity) {
        DmlColumns columns = DmlColumns.from(entity);
        return "insert into " + entityMetadata.getTableName() + " (" +
                String.join(", ", columns.getInsertColumnNames()) +
                ") values (" +
                String.join(", ", columns.getInsertColumnValues()) +
                ");";
    }

    public String buildSelectByIdQuery() {
        return "select * from " + entityMetadata.getTableName() + ";";
    }

    public String buildSelectByIdQuery(Object id) {
        return "select * from " + entityMetadata.getTableName() + " where " + entityMetadata.getPrimaryKeyName() + " = " + id + ";";
    }

    public String buildDeleteByIdQuery(Object id) {
        return "delete from " + entityMetadata.getTableName() + " where " + entityMetadata.getPrimaryKeyName() + " = " + id + ";";
    }

}
