package persistence.sql;

public abstract class QueryBuilder {
    protected final Dialect dialect;
    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public String generateSQLQuery(Object object) {
        throw new RuntimeException("Not implemented");
    }

    public QueryBuilder findBy(Object... primaryKeyValues){
        throw new RuntimeException("Not implemented");
    }

    public String generateSQLQuery(Class<?> clazz) {
        throw new RuntimeException("Not implemented");
    }
}
