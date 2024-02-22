package persistence.sql.dml;

import persistence.sql.domain.DatabaseTable;

public class DmlQueryBuilder{

    private static String INSERT_TEMPLATE = "insert into %s(%s) values(%s);";
    private static String FIND_ALL_TEMPLATE = "select * from %s;";

    public <T> String insert(T entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String columnClause = table.columnClause();
        String valueClause = table.valueClause();

        return String.format(INSERT_TEMPLATE, name, columnClause, valueClause);
    }

    public String findAll(Class<?> entity){
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();

        return String.format(FIND_ALL_TEMPLATE, name);
    }
}
