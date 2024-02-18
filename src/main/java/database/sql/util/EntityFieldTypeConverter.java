package database.sql.util;

public interface EntityFieldTypeConverter {
    String convert(Class<?> type, Integer columnLength);
}
