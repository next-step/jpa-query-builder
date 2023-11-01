package persistence.sql;

import persistence.dialect.Dialect;

public abstract class QueryBuilder {

    protected Dialect dialect;

    public QueryBuilder(Dialect dialect) {
        this.dialect = dialect;
    }

    public Query queryForObject(Object domain) {
        return new Query();
    }

    public Query queryForObject(Object domain, Long id) {
        return new Query();
    }


}
