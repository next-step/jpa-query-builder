package persistence.sql.ddl.vo;

import jakarta.persistence.GenerationType;
import java.util.Objects;
import lombok.Getter;
import persistence.sql.ddl.vo.type.DatabaseType;

@Getter
public class DatabaseField {
    private final String name;
    private final DatabaseType databaseType;
    private final boolean isPrimary;
    private final GenerationType primaryKeyGenerationType;
    private final boolean isNullable;

    public DatabaseField(String name, DatabaseType databaseType, boolean isPrimary, GenerationType primaryKeyGenerationType, boolean isNullable) {
        this.name = Objects.requireNonNull(name);
        this.databaseType = Objects.requireNonNull(databaseType);
        this.isPrimary = isPrimary;
        this.primaryKeyGenerationType = primaryKeyGenerationType;
        this.isNullable = isNullable;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    @Override
    public String toString() {
        return name + " " + databaseType + (isNullable ? "" : " not null");
    }
}
