package persistence.sql.ddl;

public interface DdlQueryBuilder {
    String build(Class<?> entityClazz);
}
