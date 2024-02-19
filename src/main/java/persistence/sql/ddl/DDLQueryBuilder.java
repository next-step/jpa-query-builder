package persistence.sql.ddl;

public interface DDLQueryBuilder {

    String query(Class<?> clz);

}
