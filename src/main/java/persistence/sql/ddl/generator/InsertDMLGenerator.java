package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public interface InsertDMLGenerator {
    String generate(Object object);
}
