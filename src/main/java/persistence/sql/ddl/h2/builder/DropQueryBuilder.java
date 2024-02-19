package persistence.sql.ddl.h2.builder;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.h2.meta.TableName;

public class DropQueryBuilder implements QueryBuilder {
    private final Class<?> clazz;

    public DropQueryBuilder(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    public String generateSQL() {
        return String.format("drop table %s;", getTableName());
    }

    private String getTableName() {
        return new TableName(clazz).getTableName();
    }
}
