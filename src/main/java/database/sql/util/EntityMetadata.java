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

    public List<String> getAllColumnNames() {
        return columnsMetadata.getAllColumnNames();
    }

    public String getJoinedAllColumnNames() {
        return String.join(", ", columnsMetadata.getAllColumnNames());
    }

    public List<String> getColumnDefinitions(TypeConverter typeConverter) {
        return columnsMetadata.getColumnDefinitions(typeConverter);
    }

    public String getPrimaryKeyColumnName() {
        return columnsMetadata.getPrimaryKeyColumnName();
    }

    public List<String> getGeneralColumnNames() {
        return columnsMetadata.getGeneralColumnNames();
    }

    public List<EntityColumn> getGeneralColumns() {
        return columnsMetadata.getGeneralColumns();
    }

    public long getPrimaryKeyValue(Object entity) {
        return columnsMetadata.getPrimaryKeyValue(entity);
    }
}
