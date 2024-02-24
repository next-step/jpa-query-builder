package persistence.sql.dialect.database;

public interface TypeMapper {

    String toSqlType(Class<?> clazz);

}
