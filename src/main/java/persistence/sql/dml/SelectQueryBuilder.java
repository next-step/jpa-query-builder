package persistence.sql.dml;

import persistence.sql.domain.DatabaseTable;

public class SelectQueryBuilder implements SelectQueryBuild {

    private static final String FIND_ALL_TEMPLATE = "select * from %s;";
    private static final String FIND_BY_ID_TEMPLATE = "select * from %s where %s=%s;";

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
}
