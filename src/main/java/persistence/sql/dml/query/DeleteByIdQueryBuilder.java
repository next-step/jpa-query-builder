package persistence.sql.dml.query;

import persistence.sql.definition.TableDefinition;

public class DeleteByIdQueryBuilder {

    public String build(Class<?> entityClass, Object id) {
        final TableDefinition tableDefinition = new TableDefinition(entityClass);
        final StringBuilder query = new StringBuilder("DELETE FROM ");
        query.append(tableDefinition.tableName());
        query.append(" WHERE id = ");

        if (id instanceof String) {
            query.append("'").append(id).append("';");
            return query.toString();
        }

        query.append(id).append(";");
        return query.toString();
    }
}
