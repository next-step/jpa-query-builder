package persistence.sql.dialect;

public interface Dialect {

    String getColumnType(Class<?> type);
}
