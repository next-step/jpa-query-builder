package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public interface UpdateDMLGenerator {
    String generate(Object entity);
}
