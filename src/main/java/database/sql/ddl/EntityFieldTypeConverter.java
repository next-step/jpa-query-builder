package database.sql.ddl;

public interface EntityFieldTypeConverter {
    String convert(Class<?> type, Integer columnLength);
}
