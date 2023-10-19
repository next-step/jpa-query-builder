package hibernate;

public interface QueryBuilder {

    String generateQuery(Class<?> clazz);
}
