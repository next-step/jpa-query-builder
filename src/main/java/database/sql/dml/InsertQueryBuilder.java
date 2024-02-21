package database.sql.dml;

import database.sql.util.EntityClassInspector;
import database.sql.util.column.Column;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static database.sql.Util.quote;

public class InsertQueryBuilder {
    private final List<Column> columnsForInserting;
    private final String queryPart;

    public InsertQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        this.columnsForInserting = inspector.getColumnsForInserting().collect(Collectors.toList());
        String columns = columnsForInserting.stream()
                .map(Column::getColumnName)
                .collect(Collectors.joining(", "));
        this.queryPart = String.format("INSERT INTO %s (%s)", tableName, columns);
    }

    public String buildQuery(Map<String, Object> valueMap) {
        return String.format("%s VALUES (%s)", queryPart, valueClauses(valueMap));
    }

    private String valueClauses(Map<String, Object> valueMap) {
        return columnsForInserting.stream()
                .map(it -> quote(valueMap.get(it.getColumnName())))
                .collect(Collectors.joining(", "));
    }
}
