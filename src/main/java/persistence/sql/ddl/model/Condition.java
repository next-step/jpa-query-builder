package persistence.sql.ddl.model;

public class Condition {
    public static final Condition DEFAULT_CONDITION = new Condition(false, true);
    private boolean unique;
    private boolean nullable;

    public boolean isUnique() {
        return unique;
    }

    public boolean isNullable() {
        return nullable;
    }

    public Condition(boolean unique, boolean nullable) {
        this.unique = unique;
        this.nullable = nullable;
    }

    public static Condition from(jakarta.persistence.Column column) {
        return new Condition(column.unique(), column.nullable());
    }
}
