package persistence.sql.ddl.converter;

public interface TypeConverter {

    String convert(Class<?> type);

}
