package persistence.sql.ddl.query.model;

public enum ConstantType {

    PK("PRIMARY KEY AUTO_INCREMENT"),
    PRIMARY_KEY("PRIMARY KEY"),
    NOT_NULL("NOT NULL");

    private final String type;

    ConstantType(final String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
