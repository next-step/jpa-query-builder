package persistence.sql.ddl;

public interface QueryBuilderAdapter {
    String executeQuery(Class<?> entityClass, DDLType ddlType);
}
