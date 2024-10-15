package persistence.sql.ddl.create.component.column;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class ColumnComponentBuilder {
    private static final String INDENT = "\t";
    private static final String COMMA_NEW_LINE = ",\n";
    public static final String NOT_NULL = "not null";
    private final StringBuilder componentBuilder = new StringBuilder();

    private ColumnComponentBuilder() {
    }

    public static ColumnComponentBuilder from(Field field) {
        ColumnComponentBuilder columnComponentBuilder = new ColumnComponentBuilder();
        columnComponentBuilder.componentBuilder
                .append(INDENT)
                .append(getTableName(field)).append(INDENT)
                .append(DataTypeConverter.convert(field.getType())).append(INDENT);

        columnComponentBuilder.appendIfNotNull(field);

        columnComponentBuilder.componentBuilder
                .append(COMMA_NEW_LINE);

        return columnComponentBuilder;
    }

    private static String getTableName(Field field) {
        if (!field.isAnnotationPresent(Column.class)
                || "".equals(field.getAnnotation(Column.class).name())) {
            return field.getName();
        }
        return field.getAnnotation(Column.class).name();
    }

    private void appendIfNotNull(Field field) {
        if (!field.isAnnotationPresent(Column.class)
                || field.getAnnotation(Column.class).nullable()) {
            return;
        }
        this.componentBuilder
                .append(NOT_NULL).append(INDENT);
    }

    /* TODO : append comment, nullable or not, etc. */

    public StringBuilder getComponentBuilder() {
        return this.componentBuilder;
    }
}
