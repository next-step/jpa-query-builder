package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;
import persistence.sql.ddl.FieldUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class DefaultUpdateDMLGenerator implements UpdateDMLGenerator {
    @Override
    public String generate(Object entity) {
        EntityFields entityFields = EntityFields.from(entity.getClass());

        List<String> fieldNames = getFieldNames(entityFields, entity);
        String set = setClause(entityFields, fieldNames, entity);
        String where = whereClause(entityFields, entity);

        return "UPDATE %s SET %s WHERE %s;".formatted(entityFields.tableName(), set, where);
    }

    private String whereClause(EntityFields entityFields, Object entity) {
        String idFieldName = entityFields.getIdFieldName();

        Object idValue = getValue(entityFields, idFieldName, entity);

        return "%s = %s".formatted(idFieldName, idValue);
    }

    private String setClause(EntityFields entityFields, List<String> fieldNames, Object object) {
        return fieldNames.stream().map(fieldName -> "%s = %s".formatted(fieldName, getValue(entityFields, fieldName, object)))
            .collect(Collectors.joining(","));
    }

    private List<String> getFieldNames(EntityFields entityFields, Object entity) {
        if (hasIdValue(entityFields, entity)) {
            return entityFields.getAllFieldNames();
        } else {
            return entityFields.getFieldNames();
        }
    }

    private boolean hasIdValue(EntityFields entityFields, Object object) {
        Field field = entityFields.getIdField();

        return FieldUtils.getValue(field, object) != null;
    }

    private Object getValue(EntityFields entityFields, String fieldName, Object object) {
        Field field = entityFields.getFieldByName(fieldName);

        Object value = FieldUtils.getValue(field, object);

        if (value == null) {
            return null;
        }

        return "'%s'".formatted(value);
    }
}
