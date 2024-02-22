package database.sql.dml;

import database.sql.util.EntityClassInspector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
        return columnNames.stream()
                .filter(conditionMap::containsKey)
                .map(columnName -> {
                    String quotedValue = quote(conditionMap.get(columnName));
                    return String.format("%s = %s", columnName, quotedValue);
                }).collect(Collectors.joining(" AND "));
    }
}
