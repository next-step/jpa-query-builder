package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;

public interface DeleteDMLGenerator {
    String generateDeleteAll(Table table);

    String generateDeleteById(Table table, Object id);
}
