package persistence.sql.ddl.h2.builder;

import persistence.sql.ddl.QueryBuilder;
import persistence.sql.ddl.h2.meta.TableName;

public class CreateQueryBuilder implements QueryBuilder {


    @Override
    public String generateSQL(final Class<?> clazz) {
        StringBuilder sb = new StringBuilder();
        sb.append("create table ");
        sb.append(getTableName(clazz));
        sb.append("\n(\n");
        sb.append(getColumnSQL(clazz));
        sb.append("\n);\n");
        return sb.toString();
    }

    private String getTableName(final Class<?> clazz) {
        return new TableName(clazz).getTableName();
    }

    private String getColumnSQL(final Class<?> clazz) {
        return new ColumnGenerator(clazz.getDeclaredFields()).generateSQL();
    }
}
