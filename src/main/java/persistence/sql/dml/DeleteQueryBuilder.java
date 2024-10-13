package persistence.sql.dml;

import persistence.sql.meta.Table;

public class DeleteQueryBuilder {
    private static final String QUERY_TEMPLATE = "DELETE FROM %s WHERE %s";

    private final Table table;

    public DeleteQueryBuilder(Class<?> entityClass) {
        this.table = new Table(entityClass);
    }

    public String delete(Object id) {
        return table.getQuery(QUERY_TEMPLATE, table.getTableName(), table.getWhereClause(id));
    }
}
