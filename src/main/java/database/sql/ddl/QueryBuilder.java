package database.sql.ddl;

import java.util.stream.Collectors;

public class QueryBuilder {
    public String buildCreateQuery(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        String columnsWithDefinition = inspector.getVisibleColumns(entityClass)
                .map(EntityColumn::toColumnDefinition)
                .collect(Collectors.joining(", "));

        return String.format("CREATE TABLE %s (%s)", tableName, columnsWithDefinition);
    }

    public String buildDeleteQuery(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();

        return String.format("DROP TABLE %s", tableName);
    }
}
