package persistence.core;

import persistence.exception.NotHavePrimaryKeyException;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class EntityMetadataModel {

    private final String tableName;

    private final EntityColumn primaryKeyColumn;

    private final Set<EntityColumn> columns;

    public EntityMetadataModel(
            String tableName,
            Collection<EntityColumn> columns) {

        assert tableName != null;
        assert columns != null;

        this.tableName = tableName;
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

    private EntityColumn findPrimaryKey() {
        return this.columns.stream()
                .filter(EntityColumn::isPrimaryKey)
                .findFirst()
                .orElseThrow(NotHavePrimaryKeyException::new);
    }
}
