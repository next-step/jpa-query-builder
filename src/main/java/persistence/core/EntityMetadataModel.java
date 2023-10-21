package persistence.core;

import persistence.exception.NotHavePrimaryKeyException;

import java.util.*;
import java.util.stream.Collectors;

public class EntityMetadataModel {

    private final String tableName;

    private final Class<?> type;

    private final EntityColumn primaryKeyColumn;

    private final Set<EntityColumn> columns;

    public EntityMetadataModel(
            String tableName,
            Class<?> type,
            Collection<EntityColumn> columns) {

        assert tableName != null;
        assert columns != null;

        this.tableName = tableName;
        this.type = type;
        this.columns = new LinkedHashSet<>(columns);

        EntityColumn primaryKeyColumn = findPrimaryKey();

        this.primaryKeyColumn = primaryKeyColumn;
        this.columns.remove(primaryKeyColumn);
    }

    public String getTableName() {
        return this.tableName;
    }

    public EntityColumn getPrimaryKeyColumn() {
        return this.primaryKeyColumn;
    }

    public Set<EntityColumn> getColumns() {
        return this.columns;
    }

    public List<String> getColumnNames() {
        return this.columns.stream()
                .map(EntityColumn::getName)
                .collect(Collectors.toUnmodifiableList());
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

    private EntityColumn findPrimaryKey() {
        return this.columns.stream()
                .filter(EntityColumn::isPrimaryKey)
                .findFirst()
                .orElseThrow(NotHavePrimaryKeyException::new);
    }
}
