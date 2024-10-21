package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;

public interface SelectDMLGenerator {
    String generateFindAll(Table table);

    String generateFindById(Table table, Object id);
}
