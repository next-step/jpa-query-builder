package database.sql.dml;

import database.sql.util.EntityClassInspector;

import java.util.List;
import java.util.Map;
import java.util.StringJoiner;

import static database.sql.Util.quote;

public class InsertQueryBuilder {
    private final String tableName;
    private final List<String> columnNamesForInserting;
    private final String columnNamesJoined;

    public InsertQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        this.tableName = inspector.getTableName();
        this.columnNamesForInserting = inspector.getColumnNamesForInserting();
        this.columnNamesJoined = inspector.getJoinedColumnNamesForInserting();
    }

    public String buildQuery(Map<String, Object> valueMap) {
        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columnNamesJoined, valueClauses(valueMap));
    }

    private String valueClauses(Map<String, Object> valueMap) {
        StringJoiner joiner = new StringJoiner(", ");
        for (String columnName : columnNamesForInserting) {
            String quote = quote(valueMap.get(columnName));
            joiner.add(quote);
        }
        return joiner.toString();
    }
}
