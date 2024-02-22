package database.sql.dml;

import database.sql.util.EntityClassInspector;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static database.sql.Util.quote;

public class InsertQueryBuilder {
    private final String tableName;
    private final List<String> columnNamesForInserting;

    public InsertQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        this.tableName = inspector.getTableName();
        this.columnNamesForInserting = inspector.getColumnNamesForInserting();
    }

    public String buildQuery(Map<String, Object> valueMap) {
        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, String.join(", ", columnClauses(valueMap)), valueClauses(valueMap));
    }

    private List<String> columnClauses(Map<String, Object> valueMap) {
        return columnNamesForInserting.stream()
                .filter(valueMap::containsKey)
                .collect(Collectors.toList());
    }

    private String valueClauses(Map<String, Object> valueMap) {
        return columnClauses(valueMap).stream()
                .map(columnName -> quote(valueMap.get(columnName)))
                .collect(Collectors.joining(", "));
    }
}
