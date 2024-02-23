package persistence.sql.ddl.builder;

import persistence.sql.meta.table.Table;

public class DropQueryBuilder implements QueryBuilder {

    @Override
    public String generateSQL(final Class<?> clazz) {
        return String.format("drop table %s;", getTableName(clazz));
    }

    private String getTableName(final Class<?> clazz) {
        return new Table(clazz).getTableName();
    }
}
