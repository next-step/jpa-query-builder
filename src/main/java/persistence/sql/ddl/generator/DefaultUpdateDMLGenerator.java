package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityTable;
import persistence.sql.ddl.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUpdateDMLGenerator implements UpdateDMLGenerator {
    @Override
    public String generate(Object entity) {
        EntityTable entityTable = EntityTable.from(entity.getClass());

        List<String> columnNames = getColumnNames(entityTable, entity);
        String set = setClause(entityTable, columnNames, entity);
        String where = whereClause(entityTable, entity);

        return "UPDATE %s SET %s WHERE %s;".formatted(entityTable.tableName(), set, where);
    }

    private String whereClause(EntityTable entityTable, Object entity) {
        String idColumnName = entityTable.getNameOfIdColumn();

        Object idValue = getValue(entityTable, idColumnName, entity);

        return "%s = %s".formatted(idColumnName, idValue);
    }

    private String setClause(EntityTable entityTable, List<String> columnNames, Object object) {
        return columnNames.stream().map(columnName -> "%s = %s".formatted(columnName, getValue(entityTable, columnName, object)))
            .collect(Collectors.joining(","));
    }

    private List<String> getColumnNames(EntityTable entityTable, Object entity) {
        if (hasIdValue(entityTable, entity)) {
            return entityTable.getAllColumnNames();
        } else {
            return entityTable.getColumnNames();
        }
    }

    private boolean hasIdValue(EntityTable entityTable, Object object) {
        Field field = entityTable.getFieldByIdColumn();

        return FieldUtils.getValue(field, object) != null;
    }

    private Object getValue(EntityTable entityTable, String columnName, Object object) {
        Field field = entityTable.getFieldByColumnName(columnName);

        Object value = FieldUtils.getValue(field, object);

        if (value == null) {
            return null;
        }

        return "'%s'".formatted(value);
    }
}
