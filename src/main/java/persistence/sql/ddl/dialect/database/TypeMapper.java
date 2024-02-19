package persistence.sql.ddl.dialect.database;

public interface TypeMapper {

    String toSqlType(Class<?> clazz);

}
