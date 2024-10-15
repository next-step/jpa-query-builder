package persistence.sql.dml.query;

import persistence.sql.definition.TableDefinition;

public class SelectByIdQueryBuilder {

    public String build(Class<?> entityClass) {
        final TableDefinition tableDefinition = new TableDefinition(entityClass);

        StringBuilder query = new StringBuilder("SELECT ");

        tableDefinition.queryableColumns().forEach(column -> query.append(column.name()).append(", "));
        query.delete(query.length() - 2, query.length());
        query.append(" FROM ").append(tableDefinition.tableName());

        whereClause(query, tableDefinition);

        return query.toString();
    }

    private void whereClause(StringBuilder selectQuery, TableDefinition tableDefinition) {
        selectQuery.append(" WHERE ");
        selectQuery.append(tableDefinition.tableId().name()).append(" = ?;");
    }

}
