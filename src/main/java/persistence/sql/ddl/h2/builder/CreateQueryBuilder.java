package persistence.sql.ddl.h2.builder;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.h2.meta.TableName;

public class CreateQueryBuilder implements QueryBuilder {


    @Override
    public String generateSQL(final Class<?> clazz) {
        return String.format("""
                create table %s
                (
                %s
                );
                """, getTableName(clazz), getColumnSQL(clazz));
    }

    private String getTableName(final Class<?> clazz) {
        return new TableName(clazz).getTableName();
    }

    private String getColumnSQL(final Class<?> clazz) {
        return new ColumnGenerator(clazz.getDeclaredFields()).generateSQL();
    }
}
