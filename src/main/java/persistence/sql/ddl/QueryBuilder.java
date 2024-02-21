package persistence.sql.ddl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class QueryBuilder {

    private static final String SPACE = " ";
    private static final String COMMA = ", ";

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s (%s);";

    private final TypeMapper typeMapper = new H2TypeMapper();
    private final ConstraintMapper constraintMapper = new H2ConstraintMapper();

    public String createTable(Class<?> clazz) {
        return String.format(
                CREATE_TABLE_QUERY,
                generateTableName(clazz),
                generateColumns(clazz.getDeclaredFields())
        );
    }

    private String generateTableName(Class<?> clazz) {
        return clazz.getSimpleName().toUpperCase();
    }

    private String generateColumns(Field[] fields) {
        return Arrays.stream(fields)
                .map(this::generateColumn)
                .collect(Collectors.joining(COMMA));
    }

    private String generateColumn(Field field) {
        return Stream.of(
                        generateColumnName(field),
                        generateColumnType(field),
                        generateColumnConstraints(field.getDeclaredAnnotations())
                )
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(SPACE));
    }

    private String generateColumnName(Field field) {
        return field.getName().toUpperCase();
    }

    private String generateColumnType(Field field) {
        return typeMapper.getType(field);
    }

    private String generateColumnConstraints(Annotation[] annotations) {
        return Arrays.stream(annotations)
                .map(this::generateConstraint)
                .collect(Collectors.joining(SPACE));
    }

    private String generateConstraint(Annotation annotation) {
        return constraintMapper.getConstraint(annotation.annotationType());
    }

}
