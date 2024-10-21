package persistence.sql.ddl.metadata;

import java.util.List;

public class EntityMetadata {

    private final TableName tableName;
    private final ColumnMetadata columnMetadata;

    private EntityMetadata(TableName tableName, ColumnMetadata columnMetadata) {
        this.tableName = tableName;
        this.columnMetadata = columnMetadata;
    }

    public static EntityMetadata from(Class<?> clazz) {
        TableName tableName = TableName.from(clazz);
        ColumnMetadata columnMetadata = ColumnMetadata.from(clazz);
        return new EntityMetadata(tableName, columnMetadata);
    }

    public String getTableName() {
        return tableName.value();
    }

    public List<String> getPrimaryKeyNames() {
        return columnMetadata.getPrimaryKeys().stream()
                .map(Column::getName)
                .toList();
    }

    public List<Column> getColumns() {
        return columnMetadata.getColumns();
    }
}
