package persistence.sql;

public interface QueryBuilder {

    public String buildQuery(Object entity);
}
