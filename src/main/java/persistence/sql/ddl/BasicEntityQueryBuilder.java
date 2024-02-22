package persistence.sql.ddl;

import java.lang.reflect.Field;

import static persistence.sql.utils.FieldConverter.getColumn;

public class BasicEntityQueryBuilder {
    public static final String SQL_CREATE_ID_COLUMN = "id INT AUTO_INCREMENT PRIMARY KEY";
    public static final String SQL_CREATE_STRING_COLUMN = "%s VARCHAR(30)";
    public static final String SQL_CREATE_INTEGER_COLUMN = "%s INT";
    public static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS %s (";
    public static final String CLOSING_PARENTHESIS = ")";
    public static final String COMMA = ",";
    private final Class<?> entity;

    public BasicEntityQueryBuilder(Class<?> entity) {
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
}
