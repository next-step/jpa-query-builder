package persistence.sql.ddl;

public interface DatabaseTypeConverter {

    String convert(Class<?> type, Integer length);
}
