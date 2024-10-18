package persistence.sql.dml.query;

import persistence.sql.Queryable;
import persistence.sql.definition.TableDefinition;

import java.util.List;
import java.util.stream.Collectors;

public class UpdateQueryBuilder {
    public String build(Object entity) {
        final Class<?> entityClass = entity.getClass();
        final TableDefinition tableDefinition = new TableDefinition(entityClass);
        final List<? extends Queryable> targetColumns = tableDefinition.queryableColumns();

        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(tableDefinition.tableName());

        query.append(" SET ");
        String columnClause = columnClause(tableDefinition, entity, query);
        query.append(columnClause);

        query.append(" WHERE id = ");
        query.append(tableDefinition.tableId().getValue(entity));
        query.append(";");

        return query.toString();
    }

    private static String columnClause(TableDefinition tableDefinition, Object entity, StringBuilder query) {
        return tableDefinition.queryableColumns().stream()
                .map(column -> {
                    final String columnValue = column.hasValue(entity) ? column.getValue(entity) : "null";
                    return column.name() + " = " + columnValue;
                }).reduce((column1, column2) -> column1 + ", " + column2).orElse("");
    }

}
