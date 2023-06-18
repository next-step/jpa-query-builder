package persistence.sql.util;

public final class ColumnValue {
    private ColumnValue() {}

    public static String build(Object value) {
        return value.getClass().equals(String.class)
                ? String.format("'%s'", value)
                : value.toString();
    }
}
