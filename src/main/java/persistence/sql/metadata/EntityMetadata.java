package persistence.sql.metadata;

import java.lang.reflect.Field;
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

    public String getPrimaryKeyName() {
        return columnMetadata.getPrimaryKey().getName();
    }

    public List<Column> getColumns() {
        return columnMetadata.getColumns();
    }

    public List<String> getInsertColumnNames() {
        return columnMetadata.getInsertColumnNames();
    }

    public List<String> getInsertColumnValues(Object entity) {
        return columnMetadata.getInsertColumnValues(entity);
    }

    public boolean hasColumn(String fieldName) {
        return columnMetadata.hasColumn(fieldName);
    }

    public Column getColumn(Field field) {
        return columnMetadata.getColumn(field.getName());
    }
}
