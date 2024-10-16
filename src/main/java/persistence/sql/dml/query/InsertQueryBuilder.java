package persistence.sql.dml.query;

import persistence.sql.Queryable;
import persistence.sql.definition.TableDefinition;

import java.util.List;

public class InsertQueryBuilder {
    private static final String EMPTY_STRING = "";

    public String build(Object entity) {
        final Class<?> entityClass = entity.getClass();
        final TableDefinition tableDefinition = new TableDefinition(entityClass);
        final List<? extends Queryable> targetColumns = tableDefinition.hasValueColumns(entity);

        StringBuilder query = new StringBuilder();
        query.append("INSERT INTO ");
        query.append(tableDefinition.tableName());

        query.append(" (");
        query.append(columnsClause(targetColumns));

        query.append(") VALUES (");
        query.append(valueClause(entity, targetColumns));
        query.append(");");

        return query.toString();
    }

    private String columnsClause(List<? extends Queryable> targetColumns) {
        return targetColumns
                .stream()
                .map(Queryable::name)
                .reduce((column1, column2) -> column1 + ", " + column2)
                .orElse(EMPTY_STRING);
    }

    private String valueClause(Object object, List<? extends Queryable> targetColumns) {
        return targetColumns
                .stream()
                .map(column -> column.getValue(object))
                .reduce((value1, value2) -> value1 + ", " + value2)
                .orElse(EMPTY_STRING);
    }

}
