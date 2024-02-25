package database.sql.dml;

import database.sql.util.EntityMetadata;

public class SelectOneQueryBuilder {
    private final String tableName;
    private final String primaryKeyColumnName;
    private final String joinedAllColumnNames;

    public SelectOneQueryBuilder(Class<?> entityClass) {
        EntityMetadata metadata = new EntityMetadata(entityClass);
        this.tableName = metadata.getTableName();
        this.primaryKeyColumnName = metadata.getPrimaryKeyColumnName();
        this.joinedAllColumnNames = metadata.getJoinedAllColumnNames();
    }

    public String buildQuery(Long id) {
        return String.format("SELECT %s FROM %s WHERE %s = %d",
                             joinedAllColumnNames,
                             tableName,
                             primaryKeyColumnName,
                             id);
    }
}
