package database.sql.util;

import database.sql.util.column.EntityColumn;
import database.sql.util.type.TypeConverter;

import java.util.List;

public class EntityMetadata {
    private final TableMetadata tableMetadata;
    private final ColumnsMetadata columnsMetadata;

    public EntityMetadata(Object entity) {
        this(entity.getClass());
    }

    public EntityMetadata(Class<?> entityClass) {
        tableMetadata = new TableMetadata(entityClass);
        columnsMetadata = new ColumnsMetadata(entityClass);
    }

    public String getTableName() {
        return tableMetadata.getTableName();
    }

    public List<String> getColumnNames() {
        return columnsMetadata.getColumnNames();
    }

    public String getJoinedColumnNames() {
        return columnsMetadata.getJoinedColumnNames();
    }

    public List<String> getColumnDefinitions(TypeConverter typeConverter) {
        return columnsMetadata.getColumnDefinitions(typeConverter);
    }

    public String getPrimaryKeyColumnName() {
        return columnsMetadata.getPrimaryKeyColumnName();
    }

    public List<String> getColumnNamesForInserting() {
        return columnsMetadata.getColumnNamesForInserting();
    }

    public List<EntityColumn> getColumnsForInserting() {
        return columnsMetadata.getColumnsForInserting();
    }

    public long getPrimaryKeyValue(Object entity) {
        return columnsMetadata.getPrimaryKeyValue(entity);
    }
}
