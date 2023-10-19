package persistence.sql.ddl.vo;

import java.util.Objects;
import lombok.Getter;
import persistence.sql.ddl.vo.type.DatabaseType;

@Getter
public class DatabaseField {
    private final String name;
    private final DatabaseType databaseType;
    private final boolean isPrimary;

    public DatabaseField(String name, DatabaseType databaseType, boolean isPrimary) {
        this.name = Objects.requireNonNull(name);
        this.databaseType = Objects.requireNonNull(databaseType);
        this.isPrimary = isPrimary;
    }

    public boolean isPrimary() {
        return isPrimary;
    }

    @Override
    public String toString() {
        return name + " " + databaseType + " " + (isPrimary ? "primary key" : "");
    }
}
