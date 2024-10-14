package persistence.sql.ddl.generator;

public interface InsertDMLGenerator {
    String generate(Object object);
}
