package database.sql.dml;

import database.sql.util.EntityMetadata;

public class SelectOneQueryBuilder {
    private final String tableName;
    private final String fieldsForSelecting;
    private final String primaryKeyColumnName;

    public SelectOneQueryBuilder(Class<?> entityClass) {
        EntityMetadata metadata = new EntityMetadata(entityClass);
        this.tableName = metadata.getTableName();
        this.fieldsForSelecting = metadata.getJoinedColumnNames();
        this.primaryKeyColumnName = metadata.getPrimaryKeyColumnName();
    }

    public String buildQuery(Long id) {
        return String.format("SELECT %s FROM %s WHERE %s = %d", fieldsForSelecting, tableName, primaryKeyColumnName, id);
    }
}
