package persistence.sql.util;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import persistence.sql.ddl.FieldType;

import java.lang.reflect.Field;
import java.util.Objects;

public class FieldUtils {
    private FieldUtils() {
        throw new AssertionError();
    }

    public static String getColumnName(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (Objects.nonNull(column) && Objects.nonNull(column.name()) && !column.name().isBlank()) {
            return column.name();
        }
        return field.getName();
    }

    public static String getDbType(Field field) {
        FieldType fieldType = FieldType.valueOf(field);
        return fieldType.getDbType();
    }

    public static boolean isQuotesNeeded(Field field) {
        FieldType fieldType = FieldType.valueOf(field);
        return fieldType.isQuotesNeeded();
    }

    public static boolean isGeneration(Field field) {
        final GeneratedValue generatedValue = field.getAnnotation(GeneratedValue.class);
        if (Objects.isNull(generatedValue)) {
            return false;
        }
        return generatedValue.strategy() == GenerationType.IDENTITY;
    }

    public static boolean isNotNull(Field field) {
        final Column column = field.getAnnotation(Column.class);
        if (Objects.isNull(column)) {
            return false;
        }
        return !column.nullable();
    }

    public static boolean isPrimaryKey(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

    public static boolean isTransient(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }
}
