package persistence.sql.ddl.schema;

import jakarta.persistence.Column;
import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;
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

    private ColumnMeta(String columnName, String columnTypeName, List<Constraint> constraintList) {
        this.columnName = columnName;
        this.columnType = columnTypeName;
        this.columnConstraint = constraintList.stream()
            .map(Constraint::getConstraint)
            .filter(constraint -> !constraint.isEmpty())
            .collect(Collectors.joining(" "));
    }

    public static ColumnMeta of(Field field, ColumnType columnType) {
        return new ColumnMeta(
            getColumnName(field),
            columnType.getType(field),
            List.of(new PrimaryKeyConstraint(field, columnType), new NotNullConstraint(field))
        );
    }

    public String getColumn() {
        if (columnConstraint.isEmpty()) {
            return COLUMN_FORMAT.formatted(columnName, columnType);
        }

        return COLUMN_FORMAT_WITH_CONSTRAINT.formatted(columnName, columnType, columnConstraint);
    }

    private static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            final Column columnAnnotation = field.getAnnotation(Column.class);
            if (!columnAnnotation.name().isEmpty()) {
                return columnAnnotation.name();
            }
        }

        return field.getName().toLowerCase();
    }
}
