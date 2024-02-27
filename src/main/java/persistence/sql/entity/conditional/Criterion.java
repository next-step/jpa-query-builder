package persistence.sql.entity.conditional;

import persistence.sql.entity.model.Operators;

public class Criterion {
    private static final String FORMAT = "'%s'";

    private final String key;
    private final String value;
    private final Operators operators;

    public Criterion(String key,
                     String value,
                     Operators operators) {
        this.key = key;
        this.value = value;
        this.operators = operators;
    }

    public String toSql() {
        return new StringBuilder(key)
                .append(operators.getValue())
                .append(String.format(FORMAT, value))
                .toString();
    }
}
