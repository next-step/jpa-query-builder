package persistence.sql;

public interface QueryBuilder {
    String build(Class<?> entityClass);
}
