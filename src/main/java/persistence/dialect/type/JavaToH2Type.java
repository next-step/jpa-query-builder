package persistence.dialect.type;

public enum JavaToH2Type {
    LONG("bigint"),
    STRING("varchar"),
    INTEGER("int");

    private final String dbType;

    JavaToH2Type(String dbType) {
        this.dbType = dbType;
    }

    public String getDbType() {
        return dbType;
    }
}
