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
}
