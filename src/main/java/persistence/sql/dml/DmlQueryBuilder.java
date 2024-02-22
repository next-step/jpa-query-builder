package persistence.sql.dml;

import persistence.sql.domain.DatabaseTable;

public class DmlQueryBuilder {

    private static String INSERT_TEMPLATE = "insert into %s(%s) values(%s);";
    private static String FIND_ALL_TEMPLATE = "select * from %s;";
    private static String FIND_BY_ID_TEMPLATE = "select * from %s where %s=%s;";

    public <T> String insert(T entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String columnClause = table.columnClause();
        String valueClause = table.valueClause();

        return String.format(INSERT_TEMPLATE, name, columnClause, valueClause);
    }

    public String findAll(Class<?> entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();

        return String.format(FIND_ALL_TEMPLATE, name);
    }

    public String findById(Class<?> entity, Object id) {
        if (id == null){
            throw new IllegalArgumentException("database id can not be null");
        }
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String idColumn = table.getIdColumnName();

        return String.format(FIND_BY_ID_TEMPLATE, name, idColumn, id);
    }
}
