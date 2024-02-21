package persistence.sql.ddl;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import persistence.sql.ddl.mapper.ConstraintMapper;
import persistence.sql.ddl.mapper.H2ConstraintMapper;
import persistence.sql.ddl.mapper.H2TypeMapper;
import persistence.sql.ddl.mapper.TypeMapper;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static common.StringConstants.COMMA;
import static common.StringConstants.SPACE;

public class QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "CREATE TABLE %s (%s);";
    private static final String DROP_TABLE_QUERY = "DROP TABLE %s;";


    private final TypeMapper typeMapper = new H2TypeMapper();
    private final ConstraintMapper constraintMapper = new H2ConstraintMapper();

    public String createTable(Class<?> clazz) {
        return String.format(
                CREATE_TABLE_QUERY,
                generateTableName(clazz),
                generateColumns(clazz.getDeclaredFields())
        );
    }

    public String dropTable(Class<?> clazz) {
        return String.format(DROP_TABLE_QUERY, generateTableName(clazz));
    }

    private String generateTableName(Class<?> clazz) {
        if (!clazz.isAnnotationPresent(Table.class)) {
            return clazz.getSimpleName().toUpperCase();
        }

        Table table = clazz.getAnnotation(Table.class);
        if (table.name().isEmpty()) {
            return clazz.getSimpleName().toUpperCase();
        }
        return table.name().toUpperCase();
    }

    private String generateColumns(Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .map(this::generateColumn)
                .collect(Collectors.joining(COMMA));
    }

    private String generateColumn(Field field) {
        return Stream.of(
                        generateColumnName(field),
                        generateColumnType(field),
                        generateColumnConstraints(field)
                )
                .filter(s -> !s.isEmpty())
                .collect(Collectors.joining(SPACE));
    }

    private String generateColumnName(Field field) {
        if (!field.isAnnotationPresent(Column.class)) {
            return field.getName().toUpperCase();
        }

        Column column = field.getAnnotation(Column.class);
        if (column.name().isEmpty()) {
            return field.getName().toUpperCase();
        }
        return column.name().toUpperCase();
    }

    private String generateColumnType(Field field) {
        return typeMapper.getType(field);
    }

    private String generateColumnConstraints(Field field) {
        return constraintMapper.getConstraints(field);
    }
}
