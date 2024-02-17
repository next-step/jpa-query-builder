package persistence.sql.ddl;

import jakarta.persistence.Id;

import java.lang.reflect.Field;
import java.util.Arrays;

import static persistence.sql.ddl.MySQLColumnType.CLOSE_BRACKET;
import static persistence.sql.ddl.MySQLColumnType.OPEN_BRACKET;

public class MySQLDdlQueryBuilder implements DdlQueryBuilder {

    private static final String COLUMN_SEPARATOR = ", ";
    private static final String CREATE_TABLE = "CREATE TABLE ";
    private static final String EMPTY_SPACE = " ";
    private static final String PRIMARY_KEY = "PRIMARY KEY";
    private static final String END_STR = ";";

    public String createQuery(Class<?> type) {
        StringBuilder sb = new StringBuilder();
        sb.append(CREATE_TABLE)
                .append(type.getSimpleName())
                .append(OPEN_BRACKET);

        String columns = Arrays.stream(type.getDeclaredFields())
                .map(this::buildColumn)
                .reduce((columnA, columnB) -> String.join(COLUMN_SEPARATOR, columnA, columnB))
                .orElseThrow(IllegalStateException::new);

        return sb.append(columns)
                .append(CLOSE_BRACKET)
                .append(END_STR)
                .toString();
    }

    private String buildColumn(Field field) {
        Class<?> fieldType = field.getType();
        String fieldName = field.getName();

        StringBuilder sb = new StringBuilder();
        sb.append(fieldName)
                .append(EMPTY_SPACE)
                .append(MySQLColumnType.convert(fieldType));

        if (field.isAnnotationPresent(Id.class)) {
            sb.append(EMPTY_SPACE)
              .append(PRIMARY_KEY);
        }
        return sb.toString();
    }
}
