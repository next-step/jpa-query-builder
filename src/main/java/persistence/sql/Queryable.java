package persistence.sql;

public interface Queryable {

    void applyToCreateQuery(StringBuilder query, Dialect dialect);

    boolean hasValue(Object object);

    String getValue(Object object);

    String name();

}
