package persistence.sql.dml;

import persistence.sql.domain.DatabaseTable;
import persistence.sql.domain.Query;

public class DeleteQueryBuilder implements DeleteQueryBuild {

    private static final String DELETE_TEMPLATE = "delete %s where %s;";

    @Override
    public <T> Query delete(T entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String whereClause = table.whereClause();

        String sql = String.format(DELETE_TEMPLATE, name, whereClause);

        return new Query(sql, table);
    }
}
