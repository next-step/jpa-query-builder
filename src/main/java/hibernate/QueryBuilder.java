package hibernate;

import jakarta.persistence.*;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.stream.Collectors;

public class QueryBuilder {

    private static final String CREATE_TABLE_QUERY = "create table %s (%s);";
    private static final String CREATE_COLUMN_QUERY = "%s %s";
    private static final String CREATE_COLUMN_QUERY_DELIMITER = ", ";

    private static final String CREATE_COLUMN_PRIMARY_KEY = " primary key";
    private static final String CREATE_COLUMN_AUTO_INCREMENT = " auto_increment";
    private static final String CREATE_COLUMN_NOT_NULL = " not null";

    public QueryBuilder() {
    }

    public String generateCreateQueries(final Class<?> clazz) {
        EntityClass entity = new EntityClass(clazz);
        String columns = fieldsToQueryColumn(clazz.getDeclaredFields());
        return String.format(CREATE_TABLE_QUERY, entity.tableName(), columns);
    }

    private String fieldsToQueryColumn(final Field[] fields) {
        return Arrays.stream(fields)
                .filter(field -> !field.isAnnotationPresent(Transient.class))
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
            if (field.isAnnotationPresent(GeneratedValue.class) && field.getAnnotation(GeneratedValue.class).strategy() == GenerationType.IDENTITY) {
                query += CREATE_COLUMN_AUTO_INCREMENT;
            }
        }
        if (field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable()) {
            query += CREATE_COLUMN_NOT_NULL;
        }
        return query;
    }

    private String parseFieldName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String name = field.getAnnotation(Column.class).name();
            if (!name.isEmpty()) {
                return name;
            }
        }
        return field.getName();
    }
}
