package persistence.sql.ddl;

public interface DDLGenerator {

    String generateCreate(Class<?> entity);
}
