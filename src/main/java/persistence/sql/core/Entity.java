package persistence.sql.core;

import jakarta.persistence.Table;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Entity {

    private final String name;

    private Entity(String name) {
        this.name = name;
    }

    public static Entity of(Class<?> clazz) {
        return new Entity(generateTableName(clazz));
    }

    private static String generateTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(jakarta.persistence.Entity.class)) {
            throw new IllegalArgumentException("This is not an entity annotation.");
        }

        Table annotation = clazz.getDeclaredAnnotation(Table.class);

        if (Objects.nonNull(annotation) && !annotation.name().isEmpty()) {
            return annotation.name();
        }

        return convertCamelCaseToSnakeCase(clazz.getSimpleName());
    }

    public String getName() {
        return name;
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
