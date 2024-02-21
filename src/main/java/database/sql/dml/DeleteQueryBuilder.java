package database.sql.dml;

import database.sql.util.EntityClassInspector;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static database.sql.Util.quote;

public class DeleteQueryBuilder {
    private final String tableName;
    private final List<String> columnNames;

    public DeleteQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        this.tableName = inspector.getTableName();
        this.columnNames = inspector.getColumnNames();
    }

    public String buildQuery(Map<String, Object> conditionMap) {
        return String.format("DELETE FROM %s WHERE %s", tableName, whereClause(conditionMap));
    }

    private String whereClause(Map<String, Object> conditionMap) {
        StringJoiner where = new StringJoiner(" AND ");
        for (String columnName : columnNames) {
            if (conditionMap.containsKey(columnName)) {
                String quotedValue = quote(conditionMap.get(columnName));
                where.add(String.format("%s = %s", columnName, quotedValue));
            }
        }
        return where.toString();
    }
}
