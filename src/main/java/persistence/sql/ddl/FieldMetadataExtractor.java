package persistence.sql.ddl;

import jakarta.persistence.Column;

import java.lang.reflect.Field;

public class FieldMetadataExtractor {

    private final Field field;

    public FieldMetadataExtractor(Field field) {
        this.field = field;
    }

    public String getDefinition() {
        StringBuilder sb = new StringBuilder();
        sb.append(getFieldName(field));
        sb.append(" ");
        sb.append(map(field.getType()));
        sb.append(" ");
        sb.append(getColumnOptionValue());

        if (sb.lastIndexOf(" ") == sb.length() - 1) {
            sb.deleteCharAt(sb.length() - 1);
        }

        return sb.toString();
    }

    private String getColumnOptionValue() {
        String columnOption = ColumnOptionFactory.createColumnOption(field);

        if (columnOption.equals("")) {
            return "";
        }

        return columnOption;
    }

    String map(Class<?> type) {
        // TODO 리팩토링
        if (type == String.class) {
            return "VARCHAR(255)";
        } else if (type == int.class || type == Integer.class) {
            return "INT";
        } else if (type == long.class || type == Long.class) {
            return "BIGINT";
        }

        throw new IllegalArgumentException("지원하지 않는 타입입니다.");
    }

    private String getFieldName(Field field) {
        if (field.isAnnotationPresent(Column.class)
                && !isAnnotationNameEmpty(field)) {
            Column column = field.getAnnotation(Column.class);
            return column.name();
        }

        return field.getName();
    }

    private boolean isAnnotationNameEmpty(Field field) {
        return field.getAnnotation(Column.class).name().equals("");
    }

}
