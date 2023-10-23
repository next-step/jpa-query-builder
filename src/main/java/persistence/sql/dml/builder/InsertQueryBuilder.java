package persistence.sql.dml.builder;

import persistence.entity.attribute.EntityAttribute;
import persistence.entity.attribute.GeneralAttribute;
import persistence.entity.attribute.id.IdAttribute;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class InsertQueryBuilder {

    public <T> String prepareStatement(EntityAttribute entityAttribute, T instance) {
        String tableName = entityAttribute.getTableName();

        List<Map.Entry<String, String>> columnNameAndValues = new ArrayList<>();

        IdAttribute idAttribute = entityAttribute.getIdAttribute();
        Optional<String> idValue = getFieldValue(instance, idAttribute.getFieldName());
        idValue.ifPresent(value -> columnNameAndValues.add(new AbstractMap.SimpleEntry<>(idAttribute.getColumnName(), value)));

        List<GeneralAttribute> generalAttributes = entityAttribute.getGeneralAttributes();
        generalAttributes.forEach(attribute -> {
            Optional<String> fieldValue = getFieldValue(instance, attribute.getFieldName());
            fieldValue.ifPresent(value -> columnNameAndValues.add(new AbstractMap.SimpleEntry<>(attribute.getColumnName(), value)));
        });

        return formatInsertStatement(tableName, columnNameAndValues);
    }

    private <T> Optional<String> getFieldValue(T instance, String fieldName) {
        try {
            Field field = instance.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object value = field.get(instance);
            return value == null ? Optional.empty() : Optional.of(String.valueOf(value));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatInsertStatement(String tableName, List<Map.Entry<String, String>> columnNameAndValues) {
        return String.format("INSERT INTO %s( %s ) VALUES ( %s )", tableName,
                columnNameAndValues.stream().map(Map.Entry::getKey).collect(Collectors.joining(", ")),
                columnNameAndValues.stream().map(value -> String.format("'%s'", value.getValue())).collect(Collectors.joining(", ")));
    }
}
