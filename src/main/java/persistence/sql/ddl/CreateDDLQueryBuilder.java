package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.StringJoiner;

public class CreateDDLQueryBuilder implements DDLQueryBuilder {

    private static final String CREATE_TABLE_PREFIX = "CREATE TABLE ";
    private static final String COLUMN_OPEN_BRACKET = " ( ";
    private static final String COLUMN_CLOSE_BRACKET = " );";
    private static final String SEPARATOR = ", ";
    private static final String SPACE = " ";
    private static final String PK_QUERY = " NOT NULL PRIMARY KEY";
    private static final String NOT_PK_QUERY = "";

    private final TypeConverter converter;

    public CreateDDLQueryBuilder(TypeConverter converter) {
        this.converter = converter;
    }

    @Override
    public String query(Class<?> clz) {
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
            query.append(name(field));
            query.append(type(field));
            query.append(primaryKey(field));

            joiner.add(query);
        });

        return joiner.toString();
    }

    private String name(Field field) {
        return field.getName();
    }

    private String type(Field field) {
        return SPACE + converter.convert(field.getType());
    }

    private String primaryKey(Field field) {
        if (hasPKAnnotation(field)) {
            return PK_QUERY;
        }
        return NOT_PK_QUERY;
    }

    private static boolean hasPKAnnotation(Field field) {
        return field.isAnnotationPresent(Id.class);
    }

}
