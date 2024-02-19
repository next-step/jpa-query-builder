package persistence.sql;

public interface QueryBuilder {

    String generateQuery(Class<?> clazz);
}
