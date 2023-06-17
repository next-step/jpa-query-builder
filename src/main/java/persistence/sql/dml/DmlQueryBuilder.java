package persistence.sql.dml;

import persistence.sql.QueryBuilder;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DmlQueryBuilder extends QueryBuilder {
    private static final String INSERT_QUERY = "insert into %s (%s) values (%s)";
    private static final String SELECT_CLAUSE = "select %s";
    private static final String FROM_CLAUSE = "from %s";
    private static final String WHERE_CLAUSE = "where %s";

    public DmlQueryBuilder(Class<?> entity) {
        super(entity);
    }

    public <T> String insert(T instance) {
        // 1. get table name
        final String tableName = getTableName();

        // 2. get columns
        List<String> fields = getAllColumnFields()
                .stream()
                .filter(isNotTransientField())
                .filter(isNotGeneratedIdField())
                .map(super::getColumnName)
                .collect(Collectors.toList());

        // 3. get values
        List<String> values = Arrays.stream(instance.getClass().getDeclaredFields())
                .filter(isNotTransientField())
                .filter(isNotGeneratedIdField())
                .map(e -> convertValue(instance, e))
                .collect(Collectors.toList());

        // 4. build insert sql
        return String.format(INSERT_QUERY, tableName, joinWithComma(fields), joinWithComma(values)) + COLON;
    }

    public String findAll() {
        StringBuilder sql = new StringBuilder();
        List<String> fields = getAllColumnFields()
                .stream()
                .filter(isNotTransientField())
                .map(super::getColumnName).toList();
        sql.append(String.format(SELECT_CLAUSE, joinWithComma(fields)));
        sql.append(BLANK);
        sql.append(String.format(FROM_CLAUSE, getTableName()));
        sql.append(COLON);
        return sql.toString();
    }

    private <T> String convertValue(T instance, Field e) {
        try {
            return convert(instance, e);
        } catch (IllegalAccessException ex) {
            throw new RuntimeException(ex);
        }
    }

    private <T> String convert(T instance, Field field) throws IllegalAccessException {
        field.setAccessible(true);
        if (field.get(instance) == null) {
            return "null";
        }
        if (field.getType().equals(String.class)) {
            return SINGLE_QUOTE + field.get(instance) + SINGLE_QUOTE;
        }
        return String.valueOf(field.get(instance));
    }
}
