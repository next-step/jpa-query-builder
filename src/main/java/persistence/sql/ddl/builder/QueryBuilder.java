package persistence.sql.ddl.builder;

public interface QueryBuilder {
    String generateSQL(final Class<?> clazz);
}
