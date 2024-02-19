package persistence.sql;

public interface QueryBuilder {

    String generateQuery(Object object);
}
