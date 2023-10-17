package persistence.sql.ddl.builder;

public interface JavaToSqlMapper {

    String convert(Class<?> tclass);
}
