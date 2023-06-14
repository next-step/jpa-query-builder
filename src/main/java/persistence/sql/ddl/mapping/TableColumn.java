package persistence.sql.ddl.mapping;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class TableColumn {

    private static final Map<Class<?>, String> JAVA_TO_JDBC_TYPE_MAPPING = new HashMap<>();

    static {
        JAVA_TO_JDBC_TYPE_MAPPING.put(Long.class, "bigint");
        JAVA_TO_JDBC_TYPE_MAPPING.put(Integer.class, "integer");
        JAVA_TO_JDBC_TYPE_MAPPING.put(String.class, "varchar");
    }

    private final Field field;

    public TableColumn(Field field) {
        this.field = field;
    }

    public String createDdlString() {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("%s %s", getColumnName(field), JAVA_TO_JDBC_TYPE_MAPPING.get(field.getType())));

        if (hasSize()) {
            Column column = field.getAnnotation(Column.class);
            builder.append(String.format("(%d)", column.length()));
        }

        if (isNotNull()) {
            builder.append(" not null");
        }

        if (isIdentifier()) {
            builder.append(getIdentifierOption());
        }

        return builder.toString();
    }

    private String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            return getNameFromColumn(field);
        }
        return field.getName();
    }

    private String getNameFromColumn(Field field) {
        Column annotation = field.getAnnotation(Column.class);
        String name = annotation.name();
        if (name.isEmpty()) {
            name = field.getName();
        }
        return name;
    }

    private boolean hasSize() {
        return field.isAnnotationPresent(Column.class) && field.getType().isAssignableFrom(String.class);
    }

    private boolean isIdentifier() {
        return field.isAnnotationPresent(Id.class);
    }

    private boolean isNotNull() {
        if (isIdentifier()) {
            return true;
        }

        if (!field.isAnnotationPresent(Column.class)) {
            return false;
        }

        Column annotation = field.getAnnotation(Column.class);
        return !annotation.nullable();
    }

    private String getIdentifierOption() {
        String identifierOption = "";

        if (!field.isAnnotationPresent(GeneratedValue.class)) {
            return identifierOption;
        }

        GeneratedValue annotation = field.getAnnotation(GeneratedValue.class);
        if (annotation.strategy() == GenerationType.IDENTITY) {
            identifierOption = " auto_increment";
        }

        return identifierOption;
    }
}
