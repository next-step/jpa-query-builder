package persistence.sql.ddl.schema;

import jakarta.persistence.Column;
import jakarta.persistence.Transient;
import java.lang.reflect.Field;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import persistence.sql.ddl.dialect.ColumnType;
import persistence.sql.ddl.schema.constraint.Constraint;
import persistence.sql.ddl.schema.constraint.NotNullConstraint;
import persistence.sql.ddl.schema.constraint.PrimaryKeyConstraint;

public class ColumnMeta {

    private final String columnName;

    private final String columnType;

    private final String columnConstraint;

    private static final String COLUMN_FORMAT = "%s %s";
    private static final String COLUMN_FORMAT_WITH_CONSTRAINT = "%s %s %s";

    private ColumnMeta(String columnName, String columnTypeName, String columnConstraint) {
        this.columnName = columnName;
        this.columnType = columnTypeName;
        this.columnConstraint = columnConstraint;
    }

    public static ColumnMeta of(Field field, ColumnType columnType) {
        return new ColumnMeta(
            getColumnName(field),
            columnType.getFieldType(field),
            joiningConstraint(
                new PrimaryKeyConstraint(field, columnType), new NotNullConstraint(field)
            )
        );
    }

    public String getColumn() {
        if (columnConstraint.isEmpty()) {
            return String.format(COLUMN_FORMAT, columnName, columnType);
        }

        return String.format(COLUMN_FORMAT_WITH_CONSTRAINT, columnName, columnType, columnConstraint);
    }

    public String getColumnName() {
        return columnName;
    }

    public static boolean isTransient(Field field) {
        return field.isAnnotationPresent(Transient.class);
    }

    private static String getColumnName(Field field) {
        if (!hasColumnAnnotationPresent(field)) {
            return field.getName().toLowerCase();
        }

        if (hasColumnAnnotationPresent(field) && field.getAnnotation(Column.class).name().isEmpty()) {
            return field.getName().toLowerCase();
        }

        return field.getAnnotation(Column.class).name();
    }

    private static boolean hasColumnAnnotationPresent(Field field) {
        return field.isAnnotationPresent(Column.class);
    }

    private static String joiningConstraint(Constraint... constraintArray) {
        return Stream.of(constraintArray)
            .map(Constraint::getConstraint)
            .filter(constraint -> !constraint.isEmpty())
            .collect(Collectors.joining(" "));
    }
}
