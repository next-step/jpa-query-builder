package persistence.sql.dml;

import static persistence.sql.ddl.common.StringConstants.COLUMN_DEFINITION_DELIMITER;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import persistence.sql.exception.constraints.UnsupportedFieldException;

public class ColumnValueTranslator {

    public String getColumnValueClause(Object entity) {
        Class<?> clazz = entity.getClass();
        return Arrays.stream(clazz.getDeclaredFields())
            .filter(field -> !field.isAnnotationPresent(Transient.class))
            .filter(field -> !field.isAnnotationPresent(Id.class))
            .sorted(Comparator.comparing(field -> field.isAnnotationPresent(Id.class) ? 0 : 1))
            .map(field -> getNativeColumnValueString(entity, field))
            .collect(Collectors.joining(COLUMN_DEFINITION_DELIMITER));
    }

    private String getNativeColumnValueString(Object entity, Field field) {
        try {
            field.setAccessible(true);
            Object columnValue = field.get(entity);

            // TODO: extract code to other delegate class
            if (columnValue.getClass().equals(Boolean.class)) {
                return columnValue == Boolean.TRUE ? "1" : "0";
            } else if (columnValue.getClass().equals(String.class)) {
                return String.format("'%s'", columnValue);
            } else if (columnValue.getClass().equals(Integer.class)) {
                return columnValue.toString();
            } else if (columnValue.getClass().equals(Long.class)) {
                return columnValue.toString();
            }
            throw new UnsupportedFieldException(field);
        } catch (IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

}
