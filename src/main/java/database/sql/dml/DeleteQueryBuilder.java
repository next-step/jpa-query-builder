package database.sql.dml;

import database.sql.util.EntityClassInspector;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static database.sql.Util.quote;

public class DeleteQueryBuilder {
    private final Class<?> entityClass;
    private final Map<String, Object> conditionMap;

    public DeleteQueryBuilder(Class<?> entityClass, Map<String, Object> conditionMap) {
        this.entityClass = entityClass;
        this.conditionMap = conditionMap;
    }

    public String buildQuery() {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
        String tableName = inspector.getTableName();
        String whereClause = whereClause(inspector);

        return String.format("DELETE FROM %s WHERE %s", tableName, whereClause);
    }

    private String whereClause(EntityClassInspector inspector) {
        List<String> whereCond = new ArrayList<>();
        inspector.getVisibleColumns().forEach(entityColumn -> {
            String columnName = entityColumn.getColumnName();
            if (conditionMap.containsKey(columnName)) {
                Object value = conditionMap.get(columnName);
                whereCond.add(String.format("%s = %s", columnName, quote(value)));
            }
        });
        return String.join(" AND ", whereCond);
    }
}
