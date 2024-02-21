package persistence.sql.ddl.dialect;

public interface Dialect {

    String createTable(Class<?> clazz);

    String dropTable(Class<?> clazz);

}
