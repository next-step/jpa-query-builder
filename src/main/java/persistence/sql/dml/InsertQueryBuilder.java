package persistence.sql.dml;

import persistence.common.EntityClazz;
import persistence.common.FieldValue;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {

    public String build(Object obj) {
        Class<?> clazz = obj.getClass();
        EntityClazz entityClazz = new EntityClazz(clazz);
        String insertQuery = "INSERT INTO " + entityClazz.getName() + " ";

        List<FieldValue> fieldValueList = new ArrayList<>();
        for (Field field : clazz.getDeclaredFields()) {
            field.setAccessible(true);
            try {
                FieldValue fieldValue = new FieldValue(field, field.get(obj).toString());
                fieldValueList.add(fieldValue);
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        insertQuery += getColumnClause(fieldValueList);
        insertQuery += getValueClause(fieldValueList);
        insertQuery += ";";
        return insertQuery;
    }

    private String getValueClause(List<FieldValue> fieldValueList) {
        String values = fieldValueList.stream()
                .filter(FieldValue::notTransient)
                .map(fv -> {
                    if (fv.getClazz().equals(String.class)) {
                        return "'" + fv.getValue() + "'";
                    }

                    return fv.getValue();
                }).collect(Collectors.joining(","));

        return "values (" + values + ") ";
    }

    private String getColumnClause(List<FieldValue> fieldValueList) {
        String fieldNames = fieldValueList.stream()
                .filter(FieldValue::notTransient)
                .map(fv -> fv.getFieldName())
                .collect(Collectors.joining(","));

        return "(" + fieldNames + ") ";
    }

}
