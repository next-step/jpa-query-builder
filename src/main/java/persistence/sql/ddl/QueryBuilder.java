package persistence.sql.ddl;

public interface QueryBuilder {
    String build(Object entity);
}
