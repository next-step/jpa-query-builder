package persistence.sql.common;

public class ColumnValue {
    private final Object value;

    public ColumnValue(Object value) {this.value = value;}

    @Override
    public String toString() {
        return value.getClass().equals(String.class)
                ? String.format("'%s'", value)
                : value.toString();
    }
}
