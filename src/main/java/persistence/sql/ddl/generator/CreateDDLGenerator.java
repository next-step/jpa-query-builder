package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;

public interface CreateDDLGenerator {
    String generate(Table table);
}
