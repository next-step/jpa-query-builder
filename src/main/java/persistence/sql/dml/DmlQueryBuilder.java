package persistence.sql.dml;

import persistence.sql.metadata.ColumnData;
import persistence.sql.metadata.ColumnDatas;
import persistence.sql.metadata.EntityData;
import persistence.sql.metadata.EntityMetadata;

import java.util.List;

public class DmlQueryBuilder {

    private static final String JOIN_DELIMITER = ", ";

    public String buildInsertQuery(Object entity) {
        EntityData entityData = EntityData.from(entity);
        ColumnDatas insertColumns = entityData.getInsertColumns();
        return "insert into " + entityData.getTableName() + " (" + String.join(", ", insertColumns.getColumnNames()) + ")"
                + " values (" + String.join(JOIN_DELIMITER, insertColumns.getColumnValues()) + ")"
                + ";";
    }

    public String buildSelectAllQuery(Class<?> entityClass) {
        EntityMetadata metadata = EntityMetadata.from(entityClass);

        return "select " + String.join(JOIN_DELIMITER, metadata.getColumnNames())
                + " from " + metadata.getTableName()
                + ";";
    }

    public String buildSelectByIdQuery(Class<?> entityClass, Object id) {
        EntityMetadata metadata = EntityMetadata.from(entityClass);

        return "select " + String.join(JOIN_DELIMITER, metadata.getColumnNames())
                + " from " + metadata.getTableName()
                + " where " + metadata.getPrimaryKeyName() + " = " + id
                + ";";
    }

    public String buildDeleteQuery(Object entity) {
        EntityData entityData = EntityData.from(entity);

        return "delete"
                + " from " + entityData.getTableName()
                + " where " + equalityExpression(entityData.getPrimaryKey())
                + ";";
    }

    public String buildUpdateQuery(Object entity) {
        EntityData entityData = EntityData.from(entity);
        List<ColumnData> columns = entityData.getColumns().getAll();

        return "update " + entityData.getTableName()
                + " set " + String.join(JOIN_DELIMITER, equalityExpressions(columns))
                + " where " + equalityExpression(entityData.getPrimaryKey())
                + ";";
    }

    private List<String> equalityExpressions(List<ColumnData> columns) {
        return columns.stream()
                .map(this::equalityExpression)
                .toList();
    }

    private String equalityExpression(ColumnData columnData) {
        return columnData.getName() + " = " + columnData.getValue();
    }
}
