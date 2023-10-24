package persistence.core;

import persistence.exception.NotHavePrimaryKeyException;

import java.util.*;
import java.util.stream.Collectors;

public class EntityMetadataModel {

    private final String tableName;

    private final Class<?> type;

    private final EntityColumn primaryKeyColumn;

    private final EntityColumns columns;

    public EntityMetadataModel(
            String tableName,
            Class<?> type,
            Collection<EntityColumn> columns) {

        assert tableName != null;
        assert columns != null;

        this.tableName = tableName;
        this.type = type;

        EntityColumn primaryKeyColumn = findPrimaryKey(columns);

        this.primaryKeyColumn = primaryKeyColumn;
        this.columns = new EntityColumns(columns, primaryKeyColumn);
    }

    public String getTableName() {
        return this.tableName;
    }

    public EntityColumn getPrimaryKeyColumn() {
        return this.primaryKeyColumn;
    }

    public EntityColumns getColumns() {
        return this.columns;
    }

    public List<String> getColumnNames() {
        return this.columns.getColumnNames();
    }

    public Class<?> getType() {
        return this.type;
    }

    public Class<?> getEntityType() {
        return this.getType();
    }

    public boolean isSameEntityType(Class<?> entityType) {
        return this.type.equals(entityType);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        EntityMetadataModel that = (EntityMetadataModel) object;
        return Objects.equals(tableName, that.tableName) && Objects.equals(type, that.type) && Objects.equals(primaryKeyColumn, that.primaryKeyColumn) && Objects.equals(columns, that.columns);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tableName, type, primaryKeyColumn, columns);
    }

    private EntityColumn findPrimaryKey(Collection<EntityColumn> columns) {
        return columns.stream()
                .filter(EntityColumn::isPrimaryKey)
                .findFirst()
                .orElseThrow(NotHavePrimaryKeyException::new);
    }
}
