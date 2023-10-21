package persistence.sql.vo;

import java.util.Objects;

public class TableName {
    private final String name;

    private TableName(String name) {
        this.name = Objects.requireNonNull(name);
    }

    public static TableName of(String name) {
        return new TableName(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
