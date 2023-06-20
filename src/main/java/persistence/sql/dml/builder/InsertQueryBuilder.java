package persistence.sql.dml.builder;

import persistence.sql.base.TableName;
import persistence.sql.dml.column.DmlColumns;

public class InsertQueryBuilder {
    private static final String INSERT_QUERY_FORMAT = "insert into %s (%s) values (%s)";
    public static final InsertQueryBuilder INSTANCE = new InsertQueryBuilder();

    private InsertQueryBuilder() {
    }

    public String insert(Object object) {
        Class<?> clazz = object.getClass();
        String tableName = new TableName(clazz).getName();
        DmlColumns columns = DmlColumns.of(object);

        return String.format(INSERT_QUERY_FORMAT, tableName, columns.getNames(), columns.getValues());
    }
}
