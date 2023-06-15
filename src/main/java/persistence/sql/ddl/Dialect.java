package persistence.sql.ddl;

public interface Dialect {
    String columnType(Class<?> clazz);
}
