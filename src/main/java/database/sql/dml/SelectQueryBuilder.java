package database.sql.dml;

import database.sql.util.EntityMetadata;

public class SelectQueryBuilder {
    private final String tableName;
    private final String fieldsForSelecting;

    public SelectQueryBuilder(Class<?> entityClass) {
        EntityMetadata metadata = new EntityMetadata(entityClass);
        this.tableName = metadata.getTableName();
        this.fieldsForSelecting = metadata.getJoinedColumnNames();
    }

    public String buildQuery() {
        return String.format("SELECT %s FROM %s", fieldsForSelecting, tableName);
    }
}
