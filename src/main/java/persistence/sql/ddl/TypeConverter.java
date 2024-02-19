package persistence.sql.ddl;

public interface TypeConverter {

    String convert(Class<?> type);

}
