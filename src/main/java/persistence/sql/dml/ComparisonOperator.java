package persistence.sql.dml;

public enum ComparisonOperator {
    EQUAL("=")
    ;
    private final String symbol;
    ComparisonOperator(String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
