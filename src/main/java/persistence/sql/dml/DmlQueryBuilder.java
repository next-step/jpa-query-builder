package persistence.sql.dml;

import persistence.sql.metadata.ColumnData;
import persistence.sql.metadata.ColumnDatas;
import persistence.sql.metadata.EntityData;
import persistence.sql.metadata.EntityMetadata;

public class DmlQueryBuilder {

    public String buildInsertQuery(Object entity) {
        EntityData entityData = EntityData.from(entity);
        ColumnDatas insertColumns = entityData.getInsertColumns();
        return "insert into " + entityData.getTableName() + " (" +
                String.join(", ", insertColumns.getColumnNames()) +
                ") values (" +
                String.join(", ", insertColumns.getColumnValues()) +
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
        EntityData entityData = EntityData.from(entity);

        return "delete from " + entityData.getTableName() + " where " + buildWhereClause(entityData.getPrimaryKey()) + ";";
    }

    private String buildWhereClause(ColumnData columnData) {
        return columnData.getName() + " = " + columnData.getValue();
    }
}
