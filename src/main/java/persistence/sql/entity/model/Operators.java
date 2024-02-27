package persistence.sql.entity.model;

public enum Operators {

    EQUALS("="),
    NOT_EQUALS("!="),
    LIKE("LIKE"),
    BETWEEN("BETWEEN");

    private final String value;

    Operators(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
