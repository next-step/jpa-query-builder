package persistence.sql.metadata;

public class PrimaryKey {

    private final String name;

    private PrimaryKey(String name) {
        this.name = name;
    }

    public static PrimaryKey of(Column column) {
        return new PrimaryKey(column.getName());
    }

    public String getName() {
        return name;
    }
}
