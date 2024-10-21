package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityTable;

public interface SelectDMLGenerator {
    String generateFindAll(EntityTable entityTable);

    String generateFindById(EntityTable entityTable, Object id);
}
