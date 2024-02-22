package persistence.sql.ddl;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class CreateQueryBuilder {

    public static final String SQL_CREATE_ID_COLUMN = "id INT AUTO_INCREMENT PRIMARY KEY";
    public static final String SQL_CREATE_STRING_COLUMN = "%s VARCHAR(30)";
    public static final String SQL_CREATE_INTEGER_COLUMN = "%s INT";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (";
    public static final String COLUMN_NAME_ID = "id";
    public static final String CLOSING_PARENTHESIS = ")";
    public static final String COMMA = ",";
    private final Class<?> entity;

    public CreateQueryBuilder(Class<?> entity) {
        this.entity = entity;
    }

    public String build() {
        StringBuilder queryBuilder = new StringBuilder(String.format(SQL_CREATE_TABLE, entity.getSimpleName()));

        Field[] fields = entity.getDeclaredFields();
        int fieldsCount = fields.length;

        for (int i = 0; i < fieldsCount; i++) {
            queryBuilder.append(getColumn(fields[i]));
            addConnector(i, fieldsCount, queryBuilder);
        }

        return queryBuilder.toString();
    }

    private static void addConnector(int currentIdx, int fieldsCount, StringBuilder queryBuilder) {
        if (isLastIndex(currentIdx, fieldsCount)) {
            queryBuilder.append(CLOSING_PARENTHESIS);
            return;
        }
        queryBuilder.append(COMMA);
    }

    private static boolean isLastIndex(int currentIdx, int fieldsCount) {
        return currentIdx == fieldsCount - 1;
    }

    private String getColumn(Field field) {
        if (field.getType().equals(Long.class) && field.getName().equals("id")) {
            return SQL_CREATE_ID_COLUMN;
        }
        if (field.getType().equals(String.class)) {
            return String.format(SQL_CREATE_STRING_COLUMN, field.getName());
        }

        if (field.getType().equals(Integer.class)) {
            return String.format(SQL_CREATE_INTEGER_COLUMN, field.getName());
        }
        throw new IllegalArgumentException("유효하지 않은 필드 타입입니다.");
    }

}
