package persistence.sql.metadata;

import java.beans.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Column {

    private final List<Annotation> annotations;
    private final Class<?> type;
    private final String name;
    private final Object value;

    private Column(String name, Object value, Class<?> type, List<Annotation> annotations) {
        this.name = name;
        this.value = value;
        this.type = type;
        this.annotations = annotations;
    }

    public static Column of(Field field, Object entity) {
        return new Column(generateColumnName(field), generateColumnValue(field, entity), field.getType(), generateColumnAnnotations(field.getDeclaredAnnotations()));
    }

    public static Column of(Field field) {
        return new Column(generateColumnName(field), null, field.getType(), generateColumnAnnotations(field.getDeclaredAnnotations()));
    }

    private static Object generateColumnValue(Field field, Object entity) {
        field.setAccessible(true);

        try {
            return field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static List<Annotation> generateColumnAnnotations(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .filter(annotation -> !annotation.annotationType().equals(Transient.class))
                .toList();
    }

    private static String generateColumnName(Field field) {
        jakarta.persistence.Column annotation = field.getDeclaredAnnotation(jakarta.persistence.Column.class);

        if (Objects.nonNull(annotation) && !annotation.name().isEmpty()) {
            return annotation.name();
        }

        return convertCamelCaseToSnakeCase(field.getName());
    }

    public String getName() {
        return name;
    }

    public List<Annotation> getAnnotations() {
        return annotations;
    }

    public Class<?> getType() {
        return type;
    }

    public Object getValue() {
        return value;
    }

    public static String convertCamelCaseToSnakeCase(String input) {
        String regex = "([a-z])([A-Z]+)";
        String replacement = "$1_$2";

        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        String result = matcher.replaceAll(replacement);

        return result.toLowerCase();
    }
}
