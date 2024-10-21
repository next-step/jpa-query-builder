package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;
import persistence.sql.ddl.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultInsertDMLGenerator implements InsertDMLGenerator {
    @Override
    public String generate(Object entity) {
        Table table = Table.from(entity.getClass());

        List<String> fieldNames = getFieldNames(table, entity);
        String columns = columnsClause(fieldNames);
        String values = valueClause(table, fieldNames, entity);

        return "INSERT INTO %s (%s) values (%s);".formatted(table.tableName(), columns, values);
    }

    private String columnsClause(List<String> fieldNames) {
        return String.join(",", fieldNames);
    }

    private String valueClause(Table table, List<String> fieldNames, Object object) {
        return fieldNames.stream().map(fieldName -> "%s".formatted(getValue(table, fieldName, object)))
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
