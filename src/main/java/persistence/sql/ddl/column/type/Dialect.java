package persistence.sql.ddl.column.type;

public interface Dialect {
    String columnType(Class<?> clazz);
}
