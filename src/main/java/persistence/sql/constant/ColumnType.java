package persistence.sql.constant;

public enum ColumnType {

    VARCHAR("VARCHAR(255)"),
    INTEGER("INTEGER"),
    BIGINT("BIGINT"),
    BOOLEAN("BOOLEAN")
    ;

    private final String type;

    ColumnType(String type) {
        this.type = type;
    }

    public String getSql() {
        return type;
    }
}
