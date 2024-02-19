package database.sql.dml;

import database.sql.IQueryBuilder;
import database.sql.util.EntityClassInspector;
import database.sql.util.EntityColumn;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static database.sql.Util.quote;


public class InsertQueryBuilder implements IQueryBuilder {
    private final Class<?> entityClass;
    private final Map<String, Object> valueMap;

    public InsertQueryBuilder(Class<?> entityClass, Map<String, Object> valueMap) {
        this.entityClass = entityClass;
        this.valueMap = valueMap;
    }

    @Override
    public String buildQuery() {
        EntityClassInspector inspector = new EntityClassInspector(entityClass);
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

    private String valueClauses(List<EntityColumn> columnsForInserting, Map<String, Object> valueMap) {
        return columnsForInserting.stream()
                .map(it -> quote(valueMap.get(it.getColumnName())))
                .collect(Collectors.joining(", "));
    }
}
