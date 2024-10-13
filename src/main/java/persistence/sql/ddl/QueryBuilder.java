package persistence.sql.ddl;

public interface QueryBuilder {
    String build(Class<?> entityClazz);
}
