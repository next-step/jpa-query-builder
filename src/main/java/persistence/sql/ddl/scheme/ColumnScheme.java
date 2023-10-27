package persistence.sql.ddl.scheme;

public class ColumnScheme {

    private final String name;
    private final String value;

    public ColumnScheme(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

}
