package database.sql.dml;

import database.sql.util.EntityMetadata;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static database.sql.Util.quote;

public class InsertQueryBuilder {
    private final String tableName;
    private final List<String> generalColumnNames;

    public InsertQueryBuilder(Class<?> entityClass) {
        EntityMetadata metadata = new EntityMetadata(entityClass);
        this.tableName = metadata.getTableName();
        this.generalColumnNames = metadata.getGeneralColumnNames();
    }

    public String buildQuery(Map<String, Object> valueMap) {
        return String.format("INSERT INTO %s (%s) VALUES (%s)",
                             tableName,
                             String.join(", ", columnClauses(valueMap)),
                             valueClauses(valueMap));
    }

    public String buildQuery(Object entity) {
        return buildQuery(valuesFromEntity(entity));
    }

    private List<String> columnClauses(Map<String, Object> valueMap) {
        return generalColumnNames.stream()
                .filter(valueMap::containsKey)
                .collect(Collectors.toList());
    }

    private String valueClauses(Map<String, Object> valueMap) {
        return columnClauses(valueMap).stream()
                .map(columnName -> quote(valueMap.get(columnName)))
                .collect(Collectors.joining(", "));
    }

    private Map<String, Object> valuesFromEntity(Object entity) {
        return new ValueMap(entity).getValuesMap();
    }
}
