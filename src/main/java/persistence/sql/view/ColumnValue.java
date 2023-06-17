package persistence.sql.view;

public class ColumnValue {
    private final String value;

    public ColumnValue(Object value) {
        this.value = value.getClass().equals(String.class)
                ? String.format("'%s'", value)
                : value.toString();
    }

    @Override
    public String toString() {
        return value;
    }
}
