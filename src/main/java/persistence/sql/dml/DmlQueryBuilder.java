package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;
import persistence.sql.ddl.metadata.ColumnName;
import persistence.sql.ddl.metadata.TableName;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class DmlQueryBuilder {

    private static final String JOIN_DELIMITER = ", ";

    public String buildInsertQuery(Object object) {
        TableName tableName = TableName.from(object.getClass());
        return "insert into " + tableName.value() + " (" +
                columnsClause(object.getClass()) +
                ") values (" +
                valueClause(object) +
                ");";
    }

    private String columnsClause(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .map(ColumnName::from)
                .map(ColumnName::value)
                .collect(Collectors.joining(JOIN_DELIMITER));
    }

    private String valueClause(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(GeneratedValue.class))
                .map(field -> getFieldValue(object, field))
                .map(this::valueToString)
                .collect(Collectors.joining(JOIN_DELIMITER));
    }

    private String valueToString(Object object) {
        if (object == null) {
            return "null";
        }

        if (object instanceof Number) {
            return object.toString();
        }

        return "'" + object + "'";
    }

    private Object getFieldValue(Object object, Field field) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new IllegalArgumentException("접근할 수 없는 필드입니다: " + field.getName());
        }
    }
}
