package persistence.sql.dml;

import java.util.Objects;

public record WhereRecord(String name, String operator, Object value) {
    public WhereRecord {
        Objects.requireNonNull(name);
        Objects.requireNonNull(operator);
        Objects.requireNonNull(value);
    }
}
