package persistence.sql.ddl.query;

public interface QueryBuilder {
    String build(Class<?> entityClazz);
}
