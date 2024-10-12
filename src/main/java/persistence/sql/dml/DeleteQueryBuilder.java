package persistence.sql.dml;

import persistence.sql.QueryBuilder;

public class DeleteQueryBuilder extends QueryBuilder {
    private static final String QUERY_TEMPLATE = "DELETE FROM %s WHERE %s";

    public DeleteQueryBuilder(Class<?> entityClass) {
        super(entityClass);
    }

    public String delete(Object id) {
        return super.build(QUERY_TEMPLATE, getTableName(), getWhereClause(id));
    }
}
