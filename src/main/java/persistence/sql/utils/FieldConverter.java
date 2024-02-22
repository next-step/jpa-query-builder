package persistence.sql.utils;

import java.lang.reflect.Field;

public class FieldConverter {

    public static final String SQL_CREATE_ID_COLUMN = "id INT AUTO_INCREMENT PRIMARY KEY";
    public static final String SQL_CREATE_STRING_COLUMN = "%s VARCHAR(30)";
    public static final String SQL_CREATE_INTEGER_COLUMN = "%s INT";
    public static final String NOT_VALID_FILED_MESSAGE = "유효하지 않은 필드 타입입니다.";

    public static String getColumn(Field field) {
        if (field.getType().equals(Long.class) && field.getName().equals("id")) {
            return SQL_CREATE_ID_COLUMN;
        }
        if (field.getType().equals(String.class)) {
            return String.format(SQL_CREATE_STRING_COLUMN, field.getName());
        }

        if (field.getType().equals(Integer.class)) {
            return String.format(SQL_CREATE_INTEGER_COLUMN, field.getName());
        }
        throw new IllegalArgumentException(NOT_VALID_FILED_MESSAGE);
    }
}
