package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class H2Column {
    public static final String BLANK_STRING = "";
    private final Field field;

    public H2Column(final Field field) {
        this.field = field;
    }

    public String generateColumnSQL() {
        StringBuilder sb = new StringBuilder();
        sb.append(getColumnName());
        sb.append(" ");
        sb.append(getDataType());
        sb.append(" ");
        sb.append(getColumnNullable());
        return sb.toString();
    }

    public String getColumnName() {
        if (hasColumnAnnotation()) {
            return getColumnNameByAnnotation();
        }
        return getColumnNameByField();
    }

    private String getColumnNameByAnnotation() {
        Column annotation = field.getAnnotation(Column.class);
        if (annotation.name().isEmpty()) {
            return getColumnNameByField();
        }
        return annotation.name();
    }

    private String getColumnNameByField() {
        return field.getName();
    }

    public String getColumnNullable() {
        if (!hasColumnAnnotation()) {
            return BLANK_STRING;
        }
        Column annotation = field.getAnnotation(Column.class);
        return Nullable.get(annotation).getSql();
    }

    public String getDataType() {
        return DataType.getDBType(field.getType().getSimpleName());
    }


    private boolean hasColumnAnnotation() {
        return field.isAnnotationPresent(Column.class);
    }
}
