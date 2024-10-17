package persistence.sql.ddl;

public interface QueryBuilder {
    String executeQuery(Class<?> entityClass);
}
