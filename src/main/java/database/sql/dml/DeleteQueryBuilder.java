package database.sql.dml;

import database.sql.util.EntityClassInspector;
import database.sql.util.column.Column;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static database.sql.Util.quote;

public class DeleteQueryBuilder {
    private final List<Column> columns;
    private final String queryPart;

    public DeleteQueryBuilder(Class<?> entityClass) {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        this.columns = inspector.getColumns().collect(Collectors.toList());
        this.queryPart = String.format("DELETE FROM %s", tableName);
    }

    public String buildQuery(Map<String, Object> conditionMap) {
        return String.format("%s WHERE %s", queryPart, whereClause(conditionMap));
    }

    private String whereClause(Map<String, Object> conditionMap) {
        List<String> whereCond = new ArrayList<>();
        columns.forEach(entityColumn -> {
            String columnName = entityColumn.getColumnName();
            if (conditionMap.containsKey(columnName)) {
                Object value = conditionMap.get(columnName);
                whereCond.add(String.format("%s = %s", columnName, quote(value)));
            }
        });
        return String.join(" AND ", whereCond);
    }
}
