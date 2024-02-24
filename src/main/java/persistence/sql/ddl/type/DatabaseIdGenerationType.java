package persistence.sql.ddl.type;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

public enum DatabaseIdGenerationType {
    NORMAL("PRIMARY KEY"),
    IDENTITY("PRIMARY KEY AUTO_INCREMENT");

    private final String sql;

    DatabaseIdGenerationType(String sql) {
        this.sql = sql;
    }

    public static DatabaseIdGenerationType from(GeneratedValue annotation) {
        if (annotation == null) {
            return NORMAL;
        }
        if (GenerationType.IDENTITY.equals(annotation.strategy())) {
            return IDENTITY;
        }
        return NORMAL;
    }

    public String toSQL() {
        return sql;
    }
}
