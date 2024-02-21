package persistence.sql.dml;

import persistence.sql.domain.Column;
import persistence.sql.domain.IdColumn;
import persistence.sql.domain.Table;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class InsertQueryBuilder {
    private static final String INSERT_QUERY_TEMPLATE = "INSERT INTO %s (%s) VALUES (%s)";
    private static final String CLAUSE_DELIMITER = ", ";

    private final Table table;
    private final Object object;

    public InsertQueryBuilder(Object object) {
        this.table = Table.of(object.getClass());
        this.object = object;
    }

    public String build() {
        List<Column> columns = table.getColumns();
        Map<String, String> clause = getClause(object, columns);
        String columnsClause = String.join(CLAUSE_DELIMITER, clause.keySet());
        String valuesClause = String.join(CLAUSE_DELIMITER, clause.values());
        return String.format(INSERT_QUERY_TEMPLATE, table.getName(), columnsClause, valuesClause);
    }

    private Map<String, String> getClause(Object object, List<Column> columns) {
        return columns.stream()
                .filter(column -> !isAutoIncrementId(column))
                .peek(column -> checkNullableValue(object, column))
                .collect(Collectors.toMap(Column::getName, column -> getDmlName(object, column),
                        (existingValue, newValue) -> existingValue, LinkedHashMap::new));
    }

    private boolean isAutoIncrementId(Column column) {
        return column.isId() && ((IdColumn) column).isAutoIncrement();
    }

    private static void checkNullableValue(Object object, Column column) {
        if (!column.isNullable() && (getObject(object, column) == null)) {
            throw new IllegalArgumentException("Not nullable column value is null");
        }
    }

    private static Object getObject(Object object, Column column) {
        try {
            Field field = column.getField();
            field.setAccessible(true);
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Failed to access field: " + column.getName(), e);
        }
    }

    private String getDmlName(Object object, Column column) {
        Object value = getObject(object, column);
        if (column.getType().isVarchar()) {
            return String.format("'%s'", value);
        }
        return value.toString();
    }
}
