package persistence.sql;

public abstract class QueryBuilder {
    protected final Dialect dialect;
    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String generateSQLQuery(Object object) throws IllegalAccessException {
        throw new RuntimeException("Not implemented");
    }
}
