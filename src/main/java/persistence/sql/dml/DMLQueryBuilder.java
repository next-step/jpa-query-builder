package persistence.sql.dml;

import java.lang.reflect.Field;
import java.util.List;
import persistence.inspector.EntityInfoExtractor;

public class DMLQueryBuilder {

    private DMLQueryBuilder() {
    }

    public static DMLQueryBuilder getInstance() {
        return new DMLQueryBuilder();
    }

    private final static String COLUMN_SEPARATOR = ", ";

    public String insertSql(Object entity) {
        String tableName = EntityInfoExtractor.getTableName(entity.getClass());
        String columns = getInsertColumnsClause(entity);
        String values = insertValueClause(entity);

        return DMLQueryFormatter.createInsertQuery(tableName, columns, values);
    }

    public String selectAllSql(Class<?> clazz) {
        String tableName = EntityInfoExtractor.getTableName(clazz);

        return DMLQueryFormatter.createSelectQuery(tableName);
    }

    public String selectByIdQuery(Class<?> clazz, Object id) {

        return DMLQueryFormatter.createSelectByConditionQuery(selectAllSql(clazz), wherePrimaryKeyClause(clazz, id));
    }

    public String deleteSql(Object entity) {
        String tableName = EntityInfoExtractor.getTableName(entity.getClass());
        String deleteConditionClause = wherePrimaryKeyClause(entity);

        return DMLQueryFormatter.createDeleteQuery(tableName, deleteConditionClause);
    }

    private String wherePrimaryKeyClause(Object object) {
        Field idField = EntityInfoExtractor.getIdField(object.getClass());

        return createCondition(EntityInfoExtractor.getColumnName(idField), EntityInfoExtractor.getFieldValue(object, idField));
    }

    private String wherePrimaryKeyClause(Class<?> clazz, Object id) {
        String columnName = EntityInfoExtractor.getIdColumnName(clazz);

        return createCondition(columnName, id);
    }

    private String createCondition(String columnName, Object value) {

        return String.format("%s = %s", columnName, formatValue(value));
    }

    private String getInsertColumnsClause(Object entity) {

        return getColumnsClause(EntityInfoExtractor.getEntityFieldsForInsert(entity.getClass()));
    }

    public String insertValueClause(Object entity) {
        StringBuilder sql = new StringBuilder();
        for (Field targetField : EntityInfoExtractor.getEntityFieldsForInsert(entity.getClass())) {
            sql.append(getFieldValueWithSqlFormat(entity, targetField))
                .append(COLUMN_SEPARATOR);
        }

        return sql.toString().replaceAll(", $", "");
    }

    private Object getFieldValueWithSqlFormat(Object entity, Field field) {
        return formatValue(EntityInfoExtractor.getFieldValue(entity, field));
    }

    private String getColumnsClause(List<Field> fields) {
        StringBuilder sql = new StringBuilder();
        final String columnFormat = "%s%s";

        for (Field field : fields) {
            sql.append(String.format(columnFormat,
                    EntityInfoExtractor.getColumnName(field),
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
