package database.sql.dml;

import database.sql.util.EntityClassInspector;
import database.sql.util.EntityColumn;

import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QueryBuilder {
    static Pattern numberPattern = Pattern.compile("^[0-9]+$");

    public String buildInsertQuery(Class<?> clazz, Map<String, String> valueMap) {
        EntityClassInspector inspector = new EntityClassInspector(clazz);
        String tableName = inspector.getTableName();
        List<EntityColumn> columnsForInserting = inspector.getColumnsForInserting().collect(Collectors.toList());
        String columns = columnClauses(columnsForInserting);
        String values = valueClauses(columnsForInserting, valueMap);

        return String.format("INSERT INTO %s (%s) VALUES (%s)", tableName, columns, values);
    }

    private String columnClauses(List<EntityColumn> columnsForInserting) {
        return columnsForInserting.stream()
                .map(EntityColumn::getColumnName)
                .collect(Collectors.joining(", "));
    }

    private String valueClauses(List<EntityColumn> columnsForInserting, Map<String, String> valueMap) {
        return columnsForInserting.stream()
                .map(it -> quote(valueMap.get(it.getColumnName())))
                .collect(Collectors.joining(", "));
    }

    private String quote(String value) {
        Matcher m = numberPattern.matcher(value);
        if (m.matches()) return value;
        return "'" + value + "'";
    }
}
