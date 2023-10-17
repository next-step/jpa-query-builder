package persistence.sql.ddl;

public interface Dialect {

    String getDialectType(Class<?> type);
}
