package persistence.sql.ddl.dialect;

public abstract class Dialect {

    public abstract String createTable(Class<?> clazz);


}
