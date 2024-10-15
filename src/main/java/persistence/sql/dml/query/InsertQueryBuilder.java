package persistence.sql.dml.query;

import persistence.sql.ColumnDefinitionAware;
import persistence.sql.definition.TableDefinition;

public class InsertQueryBuilder implements DataManipulationQueryBuilder {
    private static final String EMPTY_STRING = "";

    public InsertQueryBuilder() {
    }

    @Override
    public String build(Object entity) {
        final Class<?> entityClass = entity.getClass();
        final TableDefinition tableDefinition = new TableDefinition(entityClass);

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(tableDefinition.tableName());

        query.append(" (");
        query.append(columnsClause(entityClass));

        query.append(") VALUES (");
        query.append(valueClause(entity));
        query.append(");");

        return query.toString();
    }

    private String columnsClause(Class<?> entityClass) {
        final TableDefinition tableDefinition = new TableDefinition(entityClass);
        return tableDefinition.queryableColumns()
                .stream()
                .map(ColumnDefinitionAware::name)
                .reduce((column1, column2) -> column1 + ", " + column2)
                .orElse(EMPTY_STRING);
    }

    private String valueClause(Object object) {
        final TableDefinition tableDefinition = new TableDefinition(object.getClass());
        return tableDefinition.queryableColumns()
                .stream()
                .map(column -> column.valueAsString(object))
                .reduce((value1, value2) -> value1 + ", " + value2)
                .orElse(EMPTY_STRING);
    }

}
