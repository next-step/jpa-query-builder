package persistence.sql.ddl.converter;

public interface JavaToSqlMapper {

    String convert(Class<?> tclass);
}
