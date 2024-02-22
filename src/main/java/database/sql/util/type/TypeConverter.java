package database.sql.util.type;

public interface TypeConverter {
    String convert(Class<?> type, Integer columnLength);
}
