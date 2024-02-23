package persistence.sql.ddl;

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

public class QueryBuilder {

    private static final String DELIMITER_COMMA = ", ";
    private final Dialect dialect;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String createTableSql(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ")
                .append(generateTableName(clazz))
                .append(" (");

        sb.append(generateColumnsQuery(clazz.getDeclaredFields()));

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .forEach(field ->
                        sb.append(", PRIMARY KEY (")
                                .append(convertCamelCaseToSnakeCase(field.getName()))
                                .append(")")
                );

        sb.append(")");

        return sb.toString();
    }

    public String dropTableSql(Class<?> clazz) {
        return "DROP TABLE " + generateTableName(clazz);
    }

    private String generateColumnsQuery(Field[] fields) {
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

    private String generateTableName(Class<?> clazz) {
        Table annotation = clazz.getDeclaredAnnotation(Table.class);

        if (Objects.nonNull(annotation) && !annotation.name().isEmpty()) {
            return annotation.name();
        }

        return convertCamelCaseToSnakeCase(clazz.getSimpleName());
    }


    private String generateColumnName(Field field) {
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
