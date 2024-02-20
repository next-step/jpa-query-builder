package persistence.sql.ddl;

import jakarta.persistence.*;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Objects;

import static util.StringUtils.convertCamelCaseToSnakeCase;

public class QueryBuilder {

    private final Dialect dialect;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String createTableSql(Class<?> clazz) {
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ")
                .append(generateTableName(clazz))
                .append(" (");

        Arrays.stream(clazz.getDeclaredFields())
                .forEach(field -> generateColumn(field, sb));

        Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .forEach(field ->
                        sb.append("PRIMARY KEY (")
                                .append(convertCamelCaseToSnakeCase(field.getName()))
                                .append(")")
                );

        sb.append(")");

        return sb.toString();
    }

    public String dropTableSql(Class<?> clazz) {
        return "DROP TABLE " + generateTableName(clazz);
    }

    private String generateTableName(Class<?> clazz) {
        Table annotation = clazz.getDeclaredAnnotation(Table.class);

        if (Objects.nonNull(annotation) && !annotation.name().isEmpty()) {
            return annotation.name();
        }

        return convertCamelCaseToSnakeCase(clazz.getSimpleName());
    }

    private void generateColumn(Field field, StringBuilder sb) {
        field.setAccessible(true);

        if (field.isAnnotationPresent(Transient.class)) {
            return;
        }

        sb.append(generateColumnName(field))
                .append(" ")
                .append(dialect.getColumnType(field.getType()))
                .append(generateNotNull(field))
                .append(generateAutoIncrement(field))
                .append(", ");
    }

    private String generateNotNull(Field field) {
        Id primaryKeyAnnotation = field.getDeclaredAnnotation(Id.class);
        Column annotation = field.getDeclaredAnnotation(Column.class);

        if (Objects.nonNull(primaryKeyAnnotation) || Objects.nonNull(annotation) && !annotation.nullable()) {
            return " NOT NULL";
        }

        return "";
    }

    private String generateColumnName(Field field) {
        Column annotation = field.getDeclaredAnnotation(Column.class);

        if (Objects.nonNull(annotation) && !annotation.name().isEmpty()) {
            return annotation.name();
        }

        return convertCamelCaseToSnakeCase(field.getName());
    }

    private String generateAutoIncrement(Field field) {
        GeneratedValue annotation = field.getDeclaredAnnotation(GeneratedValue.class);

        if (Objects.nonNull(annotation) && annotation.strategy() == GenerationType.IDENTITY) {
            return " AUTO_INCREMENT";
        }

        return "";
    }
}
