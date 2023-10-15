package persistence.sql.ddl;

public interface JavaToSqlTypeMapper {

    String convert(Class<?> tclass);
}
