package persistence.sql.ddl.generator;

public interface UpdateDMLGenerator {
    String generate(Object entity);
}
