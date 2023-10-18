package persistence.sql.ddl.builder;


interface DDLQueryBuilder {
    String prepareStatement(Class<?> tClass);
}
