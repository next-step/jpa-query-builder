package database.sql.ddl;

import database.sql.IQueryBuilder;
import database.sql.util.EntityClassInspector;
import database.sql.util.EntityColumn;

import java.util.stream.Collectors;

public class CreateQueryBuilder implements IQueryBuilder {
    private final Class<?> entityClass;

    public CreateQueryBuilder(Class<?> entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public String buildQuery() {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        String columnsWithDefinition = inspector.getVisibleColumns()
                .map(EntityColumn::toColumnDefinition)
                .collect(Collectors.joining(", "));

        return String.format("CREATE TABLE %s (%s)", tableName, columnsWithDefinition);
    }
}
