package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;
import persistence.sql.ddl.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUpdateDMLGenerator implements UpdateDMLGenerator {
    @Override
    public String generate(Object entity) {
        Table table = Table.from(entity.getClass());

        List<String> fieldNames = getFieldNames(table, entity);
        String set = setClause(table, fieldNames, entity);
        String where = whereClause(table, entity);

        return "UPDATE %s SET %s WHERE %s;".formatted(table.tableName(), set, where);
    }

    private String whereClause(Table table, Object entity) {
        String idFieldName = table.getIdFieldName();

        Object idValue = getValue(table, idFieldName, entity);

        return "%s = %s".formatted(idFieldName, idValue);
    }

    private String setClause(Table table, List<String> fieldNames, Object object) {
        return fieldNames.stream().map(fieldName -> "%s = %s".formatted(fieldName, getValue(table, fieldName, object)))
            .collect(Collectors.joining(","));
    }

    private List<String> getFieldNames(Table table, Object entity) {
        if (hasIdValue(table, entity)) {
            return table.getAllFieldNames();
        } else {
            return table.getFieldNames();
        }
    }

    private boolean hasIdValue(Table table, Object object) {
        Field field = table.getIdField();

        return FieldUtils.getValue(field, object) != null;
    }

    private Object getValue(Table table, String fieldName, Object object) {
        Field field = table.getFieldByName(fieldName);

        Object value = FieldUtils.getValue(field, object);

        if (value == null) {
            return null;
        }

        return "'%s'".formatted(value);
    }
}
