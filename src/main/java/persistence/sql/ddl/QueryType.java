package persistence.sql.ddl;

public enum QueryType {
    CREATE("CREATE TABLE");

    private final String value;

    QueryType(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
