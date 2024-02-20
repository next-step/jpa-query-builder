package persistence.sql.ddl;

import persistence.inspector.EntityColumn;
import persistence.inspector.EntityMetadataInspector;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class DMLQueryBuilder_old<T> {
    private final static String COLUMN_SEPARATOR = ", ";
    private final Object entity;
    private final EntityMetadataInspector<? extends Object> entityMetadataInspector;

    public DMLQueryBuilder_old(T entity) {
        this.entity = entity;
        this.entityMetadataInspector = new EntityMetadataInspector<>(entity.getClass());
    }

    public String insertQuery() throws RuntimeException {
        final String insertQueryFormat = "INSERT INTO %s (%s) VALUES (%s)";

        List<EntityColumn> targetColumns = getEntityColumns();

        String tableName = entityMetadataInspector.getTableName();
        String columns = columnsClause(targetColumns);
        String values = valueClause(targetColumns);
        
        return String.format(insertQueryFormat, tableName, columns, values);
    }

    private List<EntityColumn> getEntityColumns() {
        return entityMetadataInspector.getEntityColumns().stream()
            .filter(EntityColumn::isInsertTargetColumn)
            .collect(Collectors.toList());
    }

    private String columnsClause(List<EntityColumn> targetColumns) {
        StringBuilder sql = new StringBuilder();
        for (EntityColumn targetColumn : targetColumns) {
            sql.append(targetColumn.getColumnName()).append(COLUMN_SEPARATOR);
        }

        return sql.toString().replaceAll(", $", "");
    }

    public String valueClause(List<EntityColumn> targetColumns) throws RuntimeException {
        StringBuilder sql = new StringBuilder();
        for (EntityColumn targetColumn : targetColumns) {
            sql.append(getFieldValueWithSqlFormat(targetColumn.getFieldName())).append(COLUMN_SEPARATOR);
        }

        return sql.toString().replaceAll(", $", "");
    }

    private Object getFieldValueWithSqlFormat(String fieldName) {
        Object fieldValue = null;
        try {
            fieldValue = getFieldValue(fieldName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        if (fieldValue instanceof String) {
            return "'" + fieldValue + "'";
        }
        return fieldValue;
    }

    private Object getFieldValue(String fieldName) throws Exception {
        Field field = entityMetadataInspector.getField(fieldName);
        field.setAccessible(true);
        return field.get(entity);
    }


}
