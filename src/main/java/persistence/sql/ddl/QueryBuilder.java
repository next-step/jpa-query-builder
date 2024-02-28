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

    public String generateCreateTableQuery(Class<?> clazz) {
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

    public String generateDropTableQuery(Class<?> clazz) {
        return "DROP TABLE " + generateTableName(clazz);
    }

    public String generateInsertQuery(Object entity) {
        return String.format("INSERT INTO %s (%s) VALUES (%s)", generateTableName(entity.getClass()), columnsClause(entity.getClass()), valueClause(entity));
    }

    public String generateSelectQuery(Object entity, Object id) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("SELECT %s FROM %s", generateColumnNames(entity.getClass().getDeclaredFields()), generateTableName(entity.getClass())));
        sb.append(whereClause(entity.getClass(), id));
        return sb.toString();
    }

    public String generateDeleteQuery(Object entity, Object id) {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("DELETE FROM %s", generateTableName(entity.getClass())));
        sb.append(whereClause(entity.getClass(), id));
        return sb.toString();
    }

    private String whereClause(Class<?> clazz, Object id) {
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

    private String generateColumnNames(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::generateColumnName)
                .collect(Collectors.joining(DELIMITER_COMMA));
    }

    private String valueClause(Object object) {
        return Arrays.stream(object.getClass().getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(field -> generateColumnValue(field, object))
                .collect(Collectors.joining(", "));
    }

    private String columnsClause(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> !field.isAnnotationPresent(Id.class))
                .map(this::generateColumnName)
                .collect(Collectors.joining(", "));
    }

    private String generateColumnValue(Field field, Object entity) {
        field.setAccessible(true);

        try {
            return field.getType().equals(String.class) ? String.format("'%s'", field.get(entity)) : String.valueOf(field.get(entity));
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
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
