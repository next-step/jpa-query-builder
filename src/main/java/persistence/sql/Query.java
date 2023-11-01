package persistence.sql;

public class Query {

    private StringBuilder query;

    public Query() {
    }
    public Query(StringBuilder query) {
        this.query = query;
    }

    public StringBuilder getQuery() {
        return query;
    }

    public void setQuery(StringBuilder query) {
        this.query = query;
    }
}
