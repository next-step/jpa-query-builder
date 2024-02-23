package persistence.sql.dml;

import persistence.sql.domain.DatabaseTable;

public class InsertQueryBuilder implements InsertQueryBuild {

    private static final String INSERT_TEMPLATE = "insert into %s(%s) values(%s);";

    public <T> String insert(T entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String columnClause = table.columnClause();
        String valueClause = table.valueClause();

        return String.format(INSERT_TEMPLATE, name, columnClause, valueClause);
    }

}
