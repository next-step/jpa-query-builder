package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;

public interface DropDDLGenerator {
    String generate(Table table);
}
