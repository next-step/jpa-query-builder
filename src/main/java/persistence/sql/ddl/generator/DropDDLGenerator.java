package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityTable;

public interface DropDDLGenerator {
    String generate(EntityTable entityTable);
}
