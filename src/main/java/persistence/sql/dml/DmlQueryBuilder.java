package persistence.sql.dml;

import persistence.sql.TableName;

public class DmlQueryBuilder {

    public String buildInsertQuery(Object instance) {
        TableName tableName = TableName.from(instance.getClass());
        DmlColumns columns = DmlColumns.from(instance);
        return "insert into " + tableName.value() + " (" +
                String.join(", ", columns.getInsertColumnNames()) +
                ") values (" +
                String.join(", ", columns.getInsertColumnValues()) +
                ");";
    }

    public String buildSelectQuery(Class<?> clazz) {
        TableName tableName = TableName.from(clazz);
        return "select * from " + tableName.value() + ";";
    }

    public String buildSelectQuery(Class<?> clazz, Object id) {
        TableName tableName = TableName.from(clazz);
        return "select * from " + tableName.value() + " where id = " + id + ";";
    }

    public String buildDeleteQuery(Class<?> clazz, Object id) {
        TableName tableName = TableName.from(clazz);
        return "delete from " + tableName.value() + " where id = " + id + ";";
    }

}
