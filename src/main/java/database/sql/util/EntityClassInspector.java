package database.sql.util;

import database.sql.util.type.TypeConverter;

import java.util.List;
import java.util.Map;

public class EntityClassInspector {
    private final TableMetadata tableMetadata;
    private final ColumnsMetadata columnsMetadata;
    private final ValueMapBuilder valueMapBuilder;

    public EntityClassInspector(Object entity) {
        this(entity.getClass());
    }

    public EntityClassInspector(Class<?> entityClass) {
        tableMetadata = new TableMetadata(entityClass);
        columnsMetadata = new ColumnsMetadata(entityClass);
        valueMapBuilder = new ValueMapBuilder(this);
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
        try {
            return (long) columnsMetadata.getPrimaryKeyValue(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    // XXX 나중에 인스펙터가 아니라 다른 곳으로 옮기기
    public Map<String, Object> buildMap(Object entity) {
        return valueMapBuilder.buildMap(entity);
    }
}
