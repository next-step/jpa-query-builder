package persistence.sql.ddl;

public enum QueryType {
    CREATE("CREATE TABLE"),
    DROP("DROP TABLE"),
    ;

    private final String value;

    QueryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public boolean isCreate() {
        return this == CREATE;
    }
}
