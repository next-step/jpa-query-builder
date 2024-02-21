package persistence.sql.column;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;

public final class MetaDataMapper {
    static final String SPACE = " ";
    private static final String EMPTY = "";
    private static final String NOT_NULL = "not null";

    private MetaDataMapper() {
    }

    public static Column of(Field field, Dialect dialect) {
        ColumnType columnType = dialect.getColumn(field.getType());
        String name = field.getName();
        String nullable = EMPTY;

        if (field.isAnnotationPresent(jakarta.persistence.Column.class)) {
            boolean isNullable = field.getAnnotation(jakarta.persistence.Column.class).nullable();
            String columnName = field.getAnnotation(jakarta.persistence.Column.class).name();

            name = convertName(columnName, name);
            nullable = getNullable(isNullable);
        }
        if (field.isAnnotationPresent(Id.class)) {
            validateGeneratedValue(field);
            GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
            IdGeneratedStrategy idGeneratedStrategy = dialect.getIdGeneratedStrategy(annotation.strategy());
            return new PkColumn(name, columnType, idGeneratedStrategy);
        }
        return new JpaColumn(name, columnType, nullable);
    }

    private static void validateGeneratedValue(Field field) {
        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            throw new IllegalArgumentException("[INFO] No @GeneratedValue annotation");
        }
    }

    private static String getNullable(boolean isNullable) {
        if (isNullable) {
            return EMPTY;
        }
        return SPACE + NOT_NULL;
    }

    private static String convertName(String columnName, String fieldName) {
        if (columnName.isBlank() || columnName.isEmpty()) {
            return fieldName;
        }
        return columnName;
    }

}
