package persistence.sql.metadata;

import java.lang.reflect.Field;
import java.util.List;

public class EntityMetadata<T> {

    private final TableName tableName;
    private final ColumnMetadata<T> columnMetadata;

    private EntityMetadata(TableName tableName, ColumnMetadata<T> columnMetadata) {
        this.tableName = tableName;
        this.columnMetadata = columnMetadata;
    }

    public static <T> EntityMetadata<T> from(Class<?> clazz) {
        TableName tableName = TableName.from(clazz);
        ColumnMetadata<T> columnMetadata = ColumnMetadata.from(clazz);
        return new EntityMetadata<>(tableName, columnMetadata);
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

    public List<String> getInsertColumnValues(T entity) {
        return columnMetadata.getInsertColumnValues(entity);
    }

    public boolean hasColumn(String fieldName) {
        return columnMetadata.hasColumn(fieldName);
    }

    public Column getColumn(Field field) {
        return columnMetadata.getColumn(field.getName());
    }
}
