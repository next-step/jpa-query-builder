package hibernate;

import jakarta.persistence.Column;
import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "create table %s (%s);";
    private static final String CREATE_COLUMN_QUERY = "%s %s";
    private static final String CREATE_COLUMN_QUERY_DELIMITER = ", ";

    private static final String CREATE_COLUMN_PRIMARY_KEY = " primary key";

    public QueryBuilder() {
    }

    public String generateCreateQueries(final Class<?> clazz) {
        String className = clazz.getSimpleName();
        String columns = fieldsToQueryColumn(clazz.getDeclaredFields());
        return String.format(CREATE_TABLE_QUERY, className, columns);
    }

    private String fieldsToQueryColumn(final Field[] fields) {
        return Arrays.stream(fields)
                .map(this::fieldToQueryColumn)
                .collect(Collectors.joining(CREATE_COLUMN_QUERY_DELIMITER));
    }

    private String fieldToQueryColumn(final Field field) {
        String columnName = parseFieldName(field);
        String columnType = ColumnType.valueOf(field.getType())
                .getH2ColumnType();

        String query = String.format(CREATE_COLUMN_QUERY, columnName, columnType);
        if (field.isAnnotationPresent(Id.class)) {
            query += CREATE_COLUMN_PRIMARY_KEY;
        }
        return query;
    }

    private static String parseFieldName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String name = field.getAnnotation(Column.class).name();
            if (!name.isEmpty()) {
                return name;
            }
        }
        return field.getName();
    }
}
