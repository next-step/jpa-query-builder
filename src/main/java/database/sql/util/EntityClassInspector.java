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

    public Field getPrimaryKeyField() {
        return columnsMetadata.getPrimaryKeyField();
    }

    public String getPrimaryKeyColumnName() {
        return columnsMetadata.getPrimaryKeyColumnName();
    }

    public List<String> getColumnNamesForInserting() {
        return columnsMetadata.getColumnNamesForInserting();
    }
}
