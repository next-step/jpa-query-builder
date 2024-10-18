package persistence.sql.ddl;

import java.util.Map;

public class EntityTableMetadata {

    private final TableName tableName;
    private final ColumnDefinitions columnDefinitions;

    public EntityTableMetadata(Class<?> entityClass) {
        this.tableName = new TableName(entityClass);
        this.columnDefinitions = new ColumnDefinitions(entityClass);
    }

    public String getTableName() {
        return tableName.getTableName();
    }

    public Map<ColumnName, ColumnType> getColumnDefinitions() {
        return columnDefinitions.getColumnDefinitions();
    }

}
