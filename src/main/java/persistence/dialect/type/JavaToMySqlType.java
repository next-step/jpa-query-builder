package persistence.dialect.type;

public enum JavaToMySqlType {
    LONG("bigint"),
    STRING("varchar"),
    INTEGER("int");

    private final String dbType;

    JavaToMySqlType(String dbType) {
        this.dbType = dbType;
    }

    public String getDbType() {
        return dbType;
    }
}
