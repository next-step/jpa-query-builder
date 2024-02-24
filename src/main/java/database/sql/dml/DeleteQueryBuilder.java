package database.sql.dml;

import database.sql.util.EntityMetadata;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static database.sql.Util.quote;

public class DeleteQueryBuilder {
    private final String tableName;
    private final List<String> allColumnNames;

    public DeleteQueryBuilder(Class<?> entityClass) {
        EntityMetadata metadata = new EntityMetadata(entityClass);
        this.tableName = metadata.getTableName();
        this.allColumnNames = metadata.getAllColumnNames();
    }

    public String buildQuery(Map<String, Object> conditionMap) {
        return String.format("DELETE FROM %s WHERE %s", tableName, whereClause(conditionMap));
    }

    private String whereClause(Map<String, Object> conditionMap) {
        return allColumnNames.stream()
                .filter(conditionMap::containsKey)
                .map(columnName -> {
                    String quotedValue = quote(conditionMap.get(columnName));
                    return String.format("%s = %s", columnName, quotedValue);
                }).collect(Collectors.joining(" AND "));
    }
}
