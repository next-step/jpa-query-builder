package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityTable;

public interface CreateDDLGenerator {
    String generate(EntityTable entityTable);
}
