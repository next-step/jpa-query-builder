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
    private final GenerationType type;
    private final boolean isNullable;

    public DatabaseField(String name, DatabaseType databaseType, boolean isPrimary, GenerationType type, boolean isNullable) {
        this.name = Objects.requireNonNull(name);
        this.databaseType = Objects.requireNonNull(databaseType);
        this.isPrimary = isPrimary;
        this.type = type;
        this.isNullable = isNullable;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    @Override
    public String toString() {
        return name + " " + databaseType + " " + (isNullable ? "" : "not null");
    }
}
