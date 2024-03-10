package persistence.sql.metadata;

public class PrimaryKeyMetadata {

    private final String name;

    private PrimaryKeyMetadata(String name) {
        this.name = name;
    }

    public static PrimaryKeyMetadata of(ColumnMetadata column) {
        return new PrimaryKeyMetadata(column.getName());
    }

    public String getName() {
        return name;
    }
}
