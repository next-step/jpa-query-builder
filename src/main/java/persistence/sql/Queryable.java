package persistence.sql;

public interface Queryable {

    void applyToCreateQuery(StringBuilder query, Dialect dialect);

    boolean hasValue(Object entity);

    String getValue(Object entity);

    String name();

}
