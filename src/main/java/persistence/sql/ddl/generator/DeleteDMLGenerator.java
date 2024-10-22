package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityTable;

public interface DeleteDMLGenerator {
    String generateDeleteAll(EntityTable entityTable);

    String generateDeleteById(EntityTable entityTable, Object id);
}
