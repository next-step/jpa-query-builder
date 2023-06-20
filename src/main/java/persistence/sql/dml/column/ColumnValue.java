package persistence.sql.dml.column;

import java.util.Objects;

public class ColumnValue {
    private static final String STRING_VALUE_FORMAT = "'%s'";
    private final Object value;

    public ColumnValue(Object value) {
        this.value = value;
    }

    public String value() {
        if (value instanceof String) {
            return String.format(STRING_VALUE_FORMAT, value);
        }

        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColumnValue that = (ColumnValue) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
