package persistence.sql.dml;

import persistence.sql.domain.DatabaseTable;

public class DeleteQueryBuilder implements DeleteQueryBuild {

    private static final String DELETE_TEMPLATE = "delete %s where %s;";

    @Override
    public <T> String delete(T entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String whereClause = table.whereClause();

        return String.format(DELETE_TEMPLATE, name, whereClause);
    }
}
