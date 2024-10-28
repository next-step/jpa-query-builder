package persistence.metadata;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

import static common.AnnotationValidation.isNotPresent;

public record ColumnMeta(int type,
                         String name,
                         int length,
                         boolean nullable) {

    private static final int DEFAULT_LENGTH = 255;

    public ColumnMeta(Field field) {
        this(
                ColumnType.getSqlType(field.getType()),
                new ColumnName(field).name(),
                getLength(field),
                getNullable(field)
        );
    }

    private static int getLength(Field field) {
        if (ColumnType.isNotVarcharType(field.getType())) {
            return 0;
        }

        if (isNotPresent(field, Column.class)) {
            return DEFAULT_LENGTH;
        }

        Column annotation = field.getAnnotation(Column.class);
        return annotation.length();
    }

    private static boolean getNullable(Field field) {
        if (isNotPresent(field, Column.class)) {
            return true;
        }
        Column annotation = field.getAnnotation(Column.class);
        return annotation.nullable();
    }

    public boolean notNull() {
        return !nullable;
    }

}
