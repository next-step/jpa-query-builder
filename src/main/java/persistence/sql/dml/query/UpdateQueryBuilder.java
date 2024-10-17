package persistence.sql.dml.query;

import persistence.sql.Queryable;
import persistence.sql.definition.TableDefinition;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UpdateQueryBuilder {
    private final Map<String, String> columns = new LinkedHashMap<>();
    private final Map<String, String> conditions = new LinkedHashMap<>();

    public void addColumn(List<? extends Queryable> queryableColumns, Object entity) {
        queryableColumns.forEach(column -> {
            final String columnName = column.name();
            final String columnValue = column.hasValue(entity) ? column.getValue(entity) : null;

            columns.put(columnName, columnValue);
        });
    }

    public void addConditions(List<? extends Queryable> queryableColumns, Object entity) {
        queryableColumns.forEach(column -> {
            final String columnName = column.name();
            final String columnValue = column.hasValue(entity) ? column.getValue(entity) : null;

            conditions.put(columnName, columnValue);
        });
    }

    public String build(Object entity) {
        if (columns.isEmpty()) {
            throw new IllegalStateException("No columns to update");
        }

        final Class<?> entityClass = entity.getClass();
        final TableDefinition tableDefinition = new TableDefinition(entityClass);

        StringBuilder query = new StringBuilder();
        query.append("UPDATE ");
        query.append(tableDefinition.tableName());

        query.append(" SET ");
        query.append(columnsClause());

        if (!conditions.isEmpty()) {
            query.append(" WHERE ");
        }
        query.append(whereClause());
        query.append(";");

        return query.toString();
    }

    private String columnsClause() {
        return columns
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.joining(", "));
    }

    private String whereClause() {
        return conditions
                .entrySet()
                .stream()
                .map(entry -> entry.getKey() + " = " + entry.getValue())
                .collect(Collectors.joining(" AND "));
    }
}
