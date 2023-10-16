package persistence.sql.ddl;

public interface QueryBuilder {

    String getQuery(Object entity);

    String getTableName(Object entity);

}
