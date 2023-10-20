package persistence.sql.ddl.converter;

public interface SqlConverter {

    String convert(Class<?> tclass);
}
