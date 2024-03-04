package persistence.sql.core;

public class PrimaryKey {

    private final String name;

    private PrimaryKey(Column column) {
        this.name = column.getName();
    }

    public static PrimaryKey of(Column column) {
        return new PrimaryKey(column);
    }

    public String getName() {
        return name;
    }
}
