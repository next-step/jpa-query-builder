package persistence.sql.ddl;

public interface QueryBuilder {

    String generate(Class<?> clazz);
}
