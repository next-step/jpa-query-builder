package persistence.sql.meta;

import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.lang.reflect.Field;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.h2.util.StringUtils;

public class Column {

    private static final Pattern CAMEL_CASE_FIELD_NAME_PATTERN = Pattern.compile("([a-z])([A-Z])");
    private static final String SNAKE_CASE_FORMAT = "%s_%s";

    private final Field field;

    private Column(Field field) {
        this.field = field;
    }

    public static Column from(Field field) {
        return new Column(field);
    }

    public String getColumnName() {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);
        if (column == null || StringUtils.isNullOrEmpty(column.name())) {
            return convertCamelToSnakeString(field.getName());
        }
        return column.name();
    }

    public Class<?> getType() {
        return field.getType();
    }

    public boolean isIdAnnotation() {
        return field.isAnnotationPresent(Id.class);
    }

    public boolean isGeneratedValueAnnotation() {
        jakarta.persistence.GeneratedValue generatedValue = field.getDeclaredAnnotation(jakarta.persistence.GeneratedValue.class);

        return generatedValue != null && generatedValue.strategy() == GenerationType.IDENTITY;
    }

    public boolean isNullable() {
        jakarta.persistence.Column column = field.getDeclaredAnnotation(jakarta.persistence.Column.class);
        return column == null || column.nullable();
    }

    private String convertCamelToSnakeString(String str) {
        Matcher matcher = CAMEL_CASE_FIELD_NAME_PATTERN.matcher(str);
        return matcher.replaceAll(matchResult -> String.format(
            SNAKE_CASE_FORMAT,
            matchResult.group(1).toLowerCase(),
            matchResult.group(2).toUpperCase()
        )).toLowerCase();
    }

    public String getFieldValue(Object object) {
        try {
            field.setAccessible(true);
            return valueOf(field.get(object));
        } catch (IllegalAccessException e) {
            return "";
        }
    }

    private String valueOf(Object object) {
        if (object instanceof String) {
            return String.format("'%s'", object);
        }
        return String.valueOf(object);
    }

    public void setFieldValue(Object object, Object value) {
        try {
            field.setAccessible(true);
            field.set(object, value);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean isInsertable() {
        return !isGeneratedValueAnnotation();
    }
}
