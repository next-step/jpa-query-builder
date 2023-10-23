package persistence.sql;

public class SqlValueMapper {
    public static final String NULL = "NULL";
    public static final String NOT_NULL = "NOT NULL";

    public static String toSqlValue(Object value) {
        if (value == null) {
            return "";
        }

        if (value instanceof String) {
            return toStringSqlValue((String) value);
        }

        return String.valueOf(value);
    }

    private static String toStringSqlValue(String value) {
        if (value.isBlank()) {
            return "";
        }

        return "'" + value + "'";
    }
}
