package persistence.sql.ddl;

import jakarta.persistence.Column;

public enum Nullable {
    NULL("NULL"),
    NOT_NULL("NOT NULL");

    private final String sql;

    Nullable(final String sql) {
        this.sql = sql;
    }

    public static Nullable get(final Column column) {
        if (column.nullable()) {
            return NULL;
        }
        return NOT_NULL;
    }

    public String getSql() {
        return sql;
    }
}
