package persistence.sql.dml;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

import persistence.entity.Person;
import persistence.inspector.EntityFieldInspector;
import persistence.inspector.EntityMetadataInspector;

public class DMLQueryBuilder {

    private final static String COLUMN_SEPARATOR = ", ";

    public String insertSql(Object entity) {
        String tableName = EntityMetadataInspector.getTableName(entity.getClass());
        String columns = getInsertColumnsClause(entity);
        String values = insertValueClause(entity);

        return createInsertQuery(tableName, columns, values);
    }

    public String createInsertQuery(String tableName, String columnClause, String valueClause) {
        final String insertQueryFormat = "INSERT INTO %s (%s) VALUES (%s)";

        return String.format(insertQueryFormat, tableName, columnClause, valueClause);
    }

    public String selectAllSql(Class<?> clazz) {
        String tableName = EntityMetadataInspector.getTableName(clazz);

        return createSelectQuery(tableName);
    }

    public String selectByIdQuery(Class<?> clazz, Object id) {
        String tableName = EntityMetadataInspector.getTableName(clazz);

        return createSelectByConditionQuery(createSelectQuery(tableName), wherePrimaryKeyClause(clazz, id));
    }

    public String deleteSql(Object entity) {
        System.out.println(entity.toString());
        String tableName = EntityMetadataInspector.getTableName(entity.getClass());
        String deleteConditionClause = whereObjectValueClause(entity);

        return createDeleteQuery(tableName, deleteConditionClause);
    }

    private String createDeleteQuery(String tableName, String conditionClause) {
        final String deleteQueryFormat = "DELETE FROM %s WHERE %s";

        return String.format(deleteQueryFormat, tableName, conditionClause);
    }

    private String createSelectByConditionQuery(String sql, String conditionClause) {
        String selectByConditionQueryFormat = "%s WHERE %s";

        return String.format(selectByConditionQueryFormat, sql, conditionClause);
    }

    private String createSelectQuery(String tableName) {

        return String.format("SELECT * FROM %s", tableName);
    }

    private String wherePrimaryKeyClause(Class<?> clazz, Object id) {
        String columnName = EntityFieldInspector.getColumnName(EntityMetadataInspector.getIdField(clazz));

        return createCondition(columnName, id);
    }

    private String whereObjectValueClause(Object entity) {
        StringBuilder sql = new StringBuilder();

        for (Field targetField : EntityMetadataInspector.getFields(entity.getClass())) {
            if (getFieldValue(entity, targetField) != null) {
                sql.append(conditionClause(targetField, getFieldValue(entity, targetField)))
                .append(" AND ");
            }
        }

        return sql.toString().replaceAll(" AND $", "");
    }

    private String conditionClause(Field field, Object value) {
        String columnName = EntityFieldInspector.getColumnName(field);

        return createCondition(columnName, value);
    }

    private String createCondition(String columnName, Object value) {

        return String.format("%s = %s", columnName, formatValue(value));
    }

    private String getInsertColumnsClause(Object entity) {

        return getColumnsClause(getEntityFieldsForInsert(entity.getClass()));
    }

    public String insertValueClause(Object entity) {
        StringBuilder sql = new StringBuilder();
        for (Field targetField : getEntityFieldsForInsert(entity.getClass())) {
            sql.append(getFieldValueWithSqlFormat(entity, targetField))
                .append(COLUMN_SEPARATOR);
        }

        return sql.toString().replaceAll(", $", "");
    }

    private List<Field> getEntityFieldsForInsert(Class<?> clazz) {
        try {

            return EntityMetadataInspector.getFields(clazz).stream()
                    .filter(this::isInsertTargetField)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isInsertTargetField(Field field) {
        return EntityFieldInspector.isPersistable(field) && !EntityFieldInspector.isAutoIncrement(field);
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

    private String getColumnsClause(List<Field> fields) {
        StringBuilder sql = new StringBuilder();
        final String columnFormat = "%s%s";

        for (Field field : fields) {
            sql.append(String.format(columnFormat,
                    EntityFieldInspector.getColumnName(field),
                    COLUMN_SEPARATOR
            ));
        }

        return sql.toString().replaceAll(", $", "");
    }

    private String formatValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }

        return value.toString();
    }

}
