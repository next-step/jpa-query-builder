package persistence.sql.ddl;

public interface DDLQueryBuilder {

    String create(Class<?> clz);

}
