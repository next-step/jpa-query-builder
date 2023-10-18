package persistence.core;

import persistence.exception.NotHavePrimaryKeyException;

import java.util.HashSet;
import java.util.Set;

public class EntityMetadataModel {

    private final String tableName;

    private final EntityColumn primaryKeyColumn;

    private final Set<EntityColumn> columns;

    public EntityMetadataModel(
            String tableName,
            Set<EntityColumn> columns) {

        assert tableName != null;
        assert columns != null;

        this.columns = new HashSet<>(columns);
        this.tableName = tableName;

        EntityColumn primaryKeyColumn = findPrimaryKey();

        this.primaryKeyColumn = primaryKeyColumn;
        this.columns.remove(primaryKeyColumn);
    }

    private EntityColumn findPrimaryKey() {
        return this.columns.stream()
                .filter(EntityColumn::isPrimaryKey)
                .findFirst()
                .orElseThrow(NotHavePrimaryKeyException::new);
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
}
