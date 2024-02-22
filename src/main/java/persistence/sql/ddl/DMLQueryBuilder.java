package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import persistence.inspector.EntityMetadataInspector;

public class DMLQueryBuilder extends QueryBuilder {

    private final static String COLUMN_SEPARATOR = ", ";

    public String insertSql(Object entity) {
        final String insertQueryFormat = "INSERT INTO %s (%s) VALUES (%s)";

        String tableName = EntityMetadataInspector.getTableName(entity.getClass());
        String columns = getInsertColumnsClause(entity);
        String values = valueClause(entity);

        return String.format(insertQueryFormat, tableName, columns, values);
    }

    public String selectSql(Class<?> clazz) {
        final String selectQueryFormat = "SELECT * FROM %s";

        String tableName = EntityMetadataInspector.getTableName(clazz);

        return String.format(selectQueryFormat, tableName);
    }


    public String selectByKeySql(Class<?> clazz, Object id) {
        String sqlFormat = "%s WHERE %s";

        return String.format(sqlFormat,
            selectSql(clazz),
            wherePrimaryKeyClause(clazz, id));
    }

    private String wherePrimaryKeyClause(Class<?> clazz, Object id) {
        return whereConditionClause(EntityMetadataInspector.getIdField(clazz), id);
    }

    private String whereConditionClause(Field field, Object value) {
        String conditionFormat = "%s = %s";

        return String.format(conditionFormat,
            EntityMetadataInspector.getColumnName(field),
            formatValue(value));
    }
    private String getInsertColumnsClause(Object entity) {
        return getColumnsClause(getEntityFieldsForInsert(entity.getClass()));
    }



    public String valueClause(Object entity) {
        StringBuilder sql = new StringBuilder();
        for (Field targetField : getEntityFieldsForInsert(entity.getClass())) {
            sql.append(getFieldValueWithSqlFormat(entity, targetField)).append(COLUMN_SEPARATOR);
        }

        return sql.toString().replaceAll(", $", "");
    }

    private List<Field> getEntityFieldsForInsert(Class<?> clazz) {
        try {
            List<Field> fields = EntityMetadataInspector.getFields(clazz);
            return fields.stream()
                    .filter(this::isInsertTargetField)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isInsertTargetField(Field field) {
        return EntityMetadataInspector.isPersistable(field) && !EntityMetadataInspector.isAutoIncrement(field);
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

}
