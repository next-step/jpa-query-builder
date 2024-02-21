package persistence.sql.ddl.mapping;

public interface QueryBuilder {

    String create(Class<?> clz);

    String drop(Class<?> clz);

}
