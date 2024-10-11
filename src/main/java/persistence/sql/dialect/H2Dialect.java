package persistence.sql.dialect;

public class H2Dialect implements Dialect {
    private final String quote = "\"";

    public H2Dialect() {
    }

    public String getQuote() {
        return quote;
    }

    @Override
    public String getIdentifierQuoted(String identifier) {
        return quote + identifier + quote;
    }

    @Override
    public String getNullString(Boolean isNull) {
        return isNull ? "NULL" : "NOT NULL";
    }
}
