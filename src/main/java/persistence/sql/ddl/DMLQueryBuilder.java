package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import persistence.inspector.EntityMetadataInspector;

public class DMLQueryBuilder {
    private final static String COLUMN_SEPARATOR = ", ";
    EntityMetadataInspector entityMetadataInspector;

    public DMLQueryBuilder() {
        this.entityMetadataInspector = new EntityMetadataInspector();
    }

    public String insertSql(Object entity) {
        final String insertQueryFormat = "INSERT INTO %s (%s) VALUES (%s)";

        String tableName = entityMetadataInspector.getTableName(entity.getClass());
        String columns = columnsClause(entity);
        String values = valueClause(entity);

        return String.format(insertQueryFormat, tableName, columns, values);
    }


    private String columnsClause(Object entity) {
        StringBuilder sql = new StringBuilder();
        for (Field targetField : getEntityFieldsForInsert(entity)) {
            sql.append(entityMetadataInspector.getColumnName(targetField)).append(COLUMN_SEPARATOR);
        }

        return sql.toString().replaceAll(", $", "");
    }

    public String valueClause(Object entity) {
        StringBuilder sql = new StringBuilder();
        for (Field targetField : getEntityFieldsForInsert(entity)) {
            sql.append(getFieldValueWithSqlFormat(entity, targetField)).append(COLUMN_SEPARATOR);
        }

        return sql.toString().replaceAll(", $", "");
    }

    private List<Field> getEntityFieldsForInsert(Object entity) {
        try {
            List<Field> fields = entityMetadataInspector.getFields(entity.getClass());
            return fields.stream()
                    .filter(this::isInsertTargetField)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isInsertTargetField(Field field) {
        return entityMetadataInspector.isPersistable(field) && !entityMetadataInspector.isAutoIncrement(field);
    }

    private Object getFieldValueWithSqlFormat(Object entity, Field field) {
        return formatValue(getFieldValue(entity, field));
    }

    private Object getFieldValue(Object entity, Field field) {
        try {
            field.setAccessible(true);
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private Object formatValue(Object fieldValue) {
        if (fieldValue instanceof String) {
            return "'" + fieldValue + "'";
        }
        return fieldValue;
    }


}
