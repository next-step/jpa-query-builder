package persistence.sql.metadata;

public record ColumnValue(
        Object value
) {

    @Override
    public String toString() {
        if (value == null) {
            return "null";
        }

        if (value instanceof Number) {
            return value.toString();
        }

        return "'" + value + "'";
    }
}
