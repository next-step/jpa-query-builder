package persistence.sql.dml;

import persistence.sql.domain.DatabaseTable;

public class DmlQueryBuilder implements DmlQueryBuild {

    private static final String INSERT_TEMPLATE = "insert into %s(%s) values(%s);";
    private static final String FIND_ALL_TEMPLATE = "select * from %s;";
    private static final String FIND_BY_ID_TEMPLATE = "select * from %s where %s=%s;";
    private static final String DELETE_TEMPLATE = "delete %s where %s;";

    @Override
    public <T> String insert(T entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String columnClause = table.columnClause();
        String valueClause = table.valueClause();

        return String.format(INSERT_TEMPLATE, name, columnClause, valueClause);
    }

    @Override
    public String findAll(Class<?> entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();

        return String.format(FIND_ALL_TEMPLATE, name);
    }

    @Override
    public String findById(Class<?> entity, Object id) {
        if (id == null) {
            throw new IllegalArgumentException("database id can not be null");
        }
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String idColumnValue = table.getIdColumnName();

        return String.format(FIND_BY_ID_TEMPLATE, name, idColumnValue, id);
    }

    @Override
    public <T> String delete(T entity) {
        DatabaseTable table = new DatabaseTable(entity);

        String name = table.getName();
        String whereClause = table.whereClause();

        return String.format(DELETE_TEMPLATE, name, whereClause);
    }
}
