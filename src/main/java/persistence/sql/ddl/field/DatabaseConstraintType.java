package persistence.sql.ddl.field;

import jakarta.persistence.Column;

public enum DatabaseConstraintType {
    NOTHING(""),
    NOT_NULL("NOT NULL");

    private final String sql;

    DatabaseConstraintType(String sql) {
        this.sql = sql;
    }

    public static DatabaseConstraintType from(Column annotation) {
        if (annotation != null && !annotation.nullable()) {
            return NOT_NULL;
        }
        return NOTHING;
    }

    public String toSQL() {
        return sql;
    }
}
