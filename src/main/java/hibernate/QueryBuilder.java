package hibernate;

public interface QueryBuilder {

    String generateCreateQuery(Class<?> clazz);
}
