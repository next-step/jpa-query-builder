package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.StringJoiner;

public class H2DDLQueryBuilder implements DDLQueryBuilder {

    private static final String CREATE_TABLE_PREFIX = "CREATE TABLE ";
    private static final String COLUMN_OPEN_BRACKET = " ( ";
    private static final String COLUMN_CLOSE_BRACKET = " );";
    private static final String SEPARATOR = ", ";
    private static final String SPACE = " ";
    private static final String PK_QUERY = " NOT NULL PRIMARY KEY";

    @Override
    public String create(Class<?> clz) {
        return new StringBuilder()
                .append(CREATE_TABLE_PREFIX)
                .append(table(clz))
                .append(COLUMN_OPEN_BRACKET)
                .append(column(clz))
                .append(COLUMN_CLOSE_BRACKET)
                .toString();
    }

    private String table(Class<?> clz) {
        return clz.getSimpleName().toLowerCase();
    }

    private String column(Class<?> clz) {
        final Field[] fields = clz.getDeclaredFields();
        StringJoiner joiner = new StringJoiner(SEPARATOR);

        Arrays.stream(fields).forEach(field -> {
            StringBuilder query = new StringBuilder();

            addColumnName(query, field);
            addColumnType(query, field);
            addPrimaryKey(query, field);
            joiner.add(query);
        });

        return joiner.toString();
    }

    private void addColumnName(StringBuilder query, Field field) {
        query.append(field.getName());
    }

    private void addColumnType(StringBuilder query, Field field) {
        final String type = H2Type.converter(field.getType());
        query.append(SPACE).append(type);
    }

    private void addPrimaryKey(StringBuilder query, Field field) {
        if (hasPKAnnotation(field)) {
            query.append(PK_QUERY);
        }
    }

    private static boolean hasPKAnnotation(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

}
