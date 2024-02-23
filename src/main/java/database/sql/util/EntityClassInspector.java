package database.sql.util;

import database.sql.util.type.TypeConverter;

import java.lang.reflect.Field;
import java.util.List;

public class EntityClassInspector {
    private final TableMetadata tableMetadata;
    private final ColumnsMetadata columnsMetadata;

    public EntityClassInspector(Class<?> entityClass) {
        tableMetadata = new TableMetadata(entityClass);
        columnsMetadata = new ColumnsMetadata(entityClass);
    }

    public EntityClassInspector(Object entity) {
        Class<?> entityClass = entity.getClass();
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

    public long getPrimaryKeyValue(Object entity) {
        Field primaryKeyField = columnsMetadata.getPrimaryKeyField();
        primaryKeyField.setAccessible(true);
        try {
            return (long) primaryKeyField.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
