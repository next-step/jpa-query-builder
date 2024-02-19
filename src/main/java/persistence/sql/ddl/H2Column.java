package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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
        if (isPK()) {
            sb.append(" ");
            sb.append(getGenerationType());
        }
        return sb.toString().trim();
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
        if (isPK()) {
            return Nullable.NOT_NULL.getSql();
        }
        if (!hasColumnAnnotation()) {
            return BLANK_STRING;
        }
        Column annotation = field.getAnnotation(Column.class);
        return Nullable.get(annotation).getSql();
    }

    public String getDataType() {
        return DataType.getDBType(field.getType().getSimpleName());
    }

    public String getGenerationType() {
        if (!hasGenerationValueAnnotation()) {
            return "";
        }
        GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
        return PKGenerationType.get(annotation).getSql();
    }


    private boolean hasColumnAnnotation() {
        return field.isAnnotationPresent(Column.class);
    }

    private boolean hasGenerationValueAnnotation() {
        return field.isAnnotationPresent(GeneratedValue.class);
    }

    private boolean isPK() {
        return field.isAnnotationPresent(Id.class);
    }
}
