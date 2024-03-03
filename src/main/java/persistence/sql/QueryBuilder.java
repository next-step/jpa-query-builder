package persistence.sql;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public abstract class QueryBuilder {

    private static final String DELIMITER_COMMA = ", ";
    private final Dialect dialect;

    public abstract String generateQuery(Class<?> clazz);

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    protected String whereClause(Class<?> clazz, Object id) {
        if (Objects.isNull(id)) {
            return "";
        }

        Field primaryKey = getPrimaryField(clazz);

        return String.format(" WHERE %s = %d", generateColumnName(primaryKey), id);
    }

    private static Field getPrimaryField(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Id field not found"));
    }

    protected String generateColumnNames(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::generateColumnName)
                .collect(Collectors.joining(DELIMITER_COMMA));
    }

    protected String generateColumnValue(Field field, Object entity) {
        field.setAccessible(true);

        try {
            return field.getType().equals(String.class) ? String.format("'%s'", field.get(entity)) : String.valueOf(field.get(entity));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    protected String generateColumnsQuery(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::generateColumnQuery)
                .collect(Collectors.joining(DELIMITER_COMMA));
    }

    private String generateColumnQuery(Field field) {
        field.setAccessible(true);

        return String.format("%s %s",
                generateColumnName(field),
                dialect.generateColumnSql(field));
    }

    protected String generateTableName(Class<?> clazz) {
        Table annotation = clazz.getDeclaredAnnotation(Table.class);

        if (Objects.nonNull(annotation) && !annotation.name().isEmpty()) {
            return annotation.name();
        }

        return convertCamelCaseToSnakeCase(clazz.getSimpleName());
    }


    protected String generateColumnName(Field field) {
        Column annotation = field.getDeclaredAnnotation(Column.class);

        if (Objects.nonNull(annotation) && !annotation.name().isEmpty()) {
            return annotation.name();
        }

        return convertCamelCaseToSnakeCase(field.getName());
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
