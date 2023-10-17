package hibernate;

public class QueryBuilder {

    public QueryBuilder() {
    }

    public String generateCreateQueries() {
        return "create table person (id bigint, age integer, name varchar);";
    }
}
