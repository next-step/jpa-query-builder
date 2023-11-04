package persistence.sql.vo;

import jakarta.persistence.GenerationType;
import java.util.Objects;
import persistence.sql.vo.type.DatabaseType;

public class DatabaseField {
    private final String databaseFieldName;
    private final String originalFieldName;
    private final DatabaseType databaseType;
    private final boolean isPrimary;
    private final GenerationType primaryKeyGenerationType;
    private final boolean isNullable;

    public DatabaseField(String databaseFieldName, String originalFieldName, DatabaseType databaseType, boolean isPrimary, GenerationType primaryKeyGenerationType, boolean isNullable) {
        this.databaseFieldName = Objects.requireNonNull(databaseFieldName);
        this.originalFieldName = Objects.requireNonNull(originalFieldName);
        this.databaseType = Objects.requireNonNull(databaseType);
        this.isPrimary = isPrimary;
        this.primaryKeyGenerationType = primaryKeyGenerationType;
        this.isNullable = isNullable;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    public GenerationType getPrimaryKeyGenerationType() {
        return primaryKeyGenerationType;
    }

    public String getDatabaseFieldName() {
        return databaseFieldName;
    }

    public String getOriginalFieldName() {
        return originalFieldName;
    }

    public DatabaseType getDatabaseType() {
        return databaseType;
    }

    public boolean isNullable() {
        return isNullable;
    }

    @Override
    public String toString() {
        return databaseFieldName + " " + databaseType + (isNullable ? "" : " not null");
    }
}
