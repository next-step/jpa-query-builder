package persistence.sql.ddl.h2.builder;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.h2.meta.TableName;

public class DropQueryBuilder implements QueryBuilder {

    @Override
    public String generateSQL(final Class<?> clazz) {
        return String.format("drop table %s;", getTableName(clazz));
    }

    private String getTableName(final Class<?> clazz) {
        return new TableName(clazz).getTableName();
    }
}
