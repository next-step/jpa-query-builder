package persistence.sql.view;

public final class ColumnValue {
    private ColumnValue() {}

    public static String render(Object value) {
        return value.getClass().equals(String.class)
                ? String.format("'%s'", value)
                : value.toString();
    }
}
