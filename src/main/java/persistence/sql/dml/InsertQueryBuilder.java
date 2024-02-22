package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;
import persistence.sql.column.*;
import persistence.sql.dialect.Database;
import persistence.sql.dialect.Dialect;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class InsertQueryBuilder {

    private static final String INSERT_QUERY_FORMAT = "insert into %s (%s) values (%s)";
    private static final String COMMA = ", ";
    private static final String QUOTES = "'";

    public String generate(Object entity, Database database) {
        ColumnGenerator columnGenerator = new ColumnGenerator(new GeneralColumnFactory());

        Class<?> clazz = entity.getClass();
        TableColumn tableColumn = TableColumn.from(clazz);
        Dialect dialect = database.createDialect();
        List<Column> columns = columnGenerator.of(clazz.getDeclaredFields(), dialect);

        return String.format(INSERT_QUERY_FORMAT,
                tableColumn.getName(),
                columnsClause(columns),
                valueClause(clazz.getDeclaredFields(), entity, dialect)
        );
    }

    private String columnsClause(List<Column> columns) {
        return columns.stream()
                .filter(column -> !column.isPk())
                .map(Column::getColumnName)
                .collect(Collectors.joining(COMMA));
    }

    private String valueClause(Field[] fields, Object entity, Dialect dialect) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(field -> isNotAutoIncrement(field, dialect))
                .map(field -> getFieldValue(entity, field))
                .collect(Collectors.joining(COMMA));
    }

    private boolean isNotAutoIncrement(Field field, Dialect dialect) {
        if (field.isAnnotationPresent(GeneratedValue.class)) {
            IdGeneratedStrategy idGeneratedStrategy = dialect.getIdGeneratedStrategy(field.getAnnotation(GeneratedValue.class).strategy());
            return !idGeneratedStrategy.isAutoIncrement();
        }
        return true;
    }

    private String getFieldValue(Object entity, Field field) {
        Object value = null;
        field.setAccessible(true);
        try {
            value = field.get(entity);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (value instanceof String) {
            return QUOTES + value + QUOTES;
        }
        return String.valueOf(value);
    }
}
