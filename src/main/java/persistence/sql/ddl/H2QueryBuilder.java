package persistence.sql.ddl;

public interface H2QueryBuilder {

    String createDdl(Class<?> clazz);

    String dropTable(Class<?> clazz);
}
