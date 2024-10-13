package persistence.sql.ddl;

public interface Queryable {
    void apply(StringBuilder query);
}
