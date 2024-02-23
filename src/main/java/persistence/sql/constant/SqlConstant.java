package persistence.sql.constant;

public enum SqlConstant {

    COMMA(","),
    SPACE(" "),
    EMPTY(""),
    EQUALS("=");

    private final String value;

    SqlConstant(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
