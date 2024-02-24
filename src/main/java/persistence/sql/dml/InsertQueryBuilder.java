package persistence.sql.dml;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Transient;
import persistence.sql.column.*;
import persistence.sql.dialect.Database;
import persistence.sql.dialect.Dialect;

import javax.xml.crypto.Data;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class InsertQueryBuilder {

    private static final String INSERT_QUERY_FORMAT = "insert into %s (%s) values (%s)";
    private static final String COMMA = ", ";
    private static final String QUOTES = "'";

    private final TableColumn tableColumn;

    public InsertQueryBuilder(TableColumn tableColumn) {
        this.tableColumn = tableColumn;
    }

    public String build(Object entity) {
        Class<?> clazz = entity.getClass();
        Database database = this.tableColumn.getDatabase();
        TableColumn tableColumn = TableColumn.from(clazz, database);

        return String.format(INSERT_QUERY_FORMAT,
                tableColumn.getName(),
                tableColumn.getColumns().insertColumnsClause(),
                valueClause(clazz.getDeclaredFields(), entity, database.createDialect())
        );
    }

    private String valueClause(Field[] fields, Object entity, Dialect dialect) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
                .filter(dialect::isNotAutoIncrement)
                .map(field -> getFieldValue(entity, field))
                .collect(Collectors.joining(COMMA));
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
