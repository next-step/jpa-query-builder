package persistence.core;

import jakarta.persistence.GeneratedValue;
import persistence.exception.NotHavePrimaryKeyException;

import java.util.Set;

public class EntityMetadataModel {

    private final String tableName;

    private final Class<?> primaryKeyType;

    private final GeneratedValue generatedValue;

    private final Set<EntityColumn> columns;

    public EntityMetadataModel(
            String tableName,
            Set<EntityColumn> columns) {
        this.tableName = tableName;
        this.columns = columns;

        EntityColumn primaryKeyColumn = findPrimaryKey();

        this.primaryKeyType = primaryKeyColumn.getType();
        this.generatedValue = primaryKeyColumn.getGeneratedValue();
    }

    private EntityColumn findPrimaryKey() {
        return this.columns.stream()
                .filter(EntityColumn::isPrimaryKey)
                .findFirst()
                .orElseThrow(NotHavePrimaryKeyException::new);
    }
}
