package persistence.sql.ddl.generator;

import persistence.sql.ddl.Table;

public final class DefaultDropDDLGenerator implements DropDDLGenerator {
    @Override
    public String generate(Table table) {
        String name = table.tableName();

        return "DROP TABLE %s;".formatted(name);
    }
}
