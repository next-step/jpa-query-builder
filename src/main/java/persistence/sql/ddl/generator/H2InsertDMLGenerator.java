package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class H2InsertDMLGenerator implements InsertDMLGenerator{
    @Override
    public String generate(Object entity) {
        EntityFields entityFields = EntityFields.from(entity.getClass());

        List<String> fieldNames = entityFields.getFieldNames();
        String columns = columnsClause(fieldNames);
        String values = valueClause(entityFields, fieldNames, entity);

        return "INSERT INTO %s (%s) values (%s);".formatted(entityFields.tableName(), columns, values);
    }

    private String columnsClause(List<String> fieldNames) {
         return String.join(",", fieldNames);
    }

    private String valueClause(EntityFields entityFields, List<String> fieldNames, Object object) {
        return fieldNames.stream().map(fieldName -> "'%s'".formatted(getValue(entityFields, fieldName, object)))
                .collect(Collectors.joining(","));
    }

    private Object getValue(EntityFields entityFields, String fieldName, Object object) {
        try {
            Field field = entityFields.getFieldByName(fieldName);

            field.setAccessible(true);

            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
