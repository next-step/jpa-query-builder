package persistence.sql;

public interface Queryable {
    void apply(StringBuilder query, Dialect dialect);
}
