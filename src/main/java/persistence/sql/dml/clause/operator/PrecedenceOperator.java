package persistence.sql.dml.clause.operator;

public enum PrecedenceOperator {
    AND("AND"),
    OR("OR"),
    NONE("")
    ;

    private final String sqlOperator;

    PrecedenceOperator(String sqlOperator) {
        this.sqlOperator = sqlOperator;
    }

    public String sqlOperator() {
        return sqlOperator;
    }
}
