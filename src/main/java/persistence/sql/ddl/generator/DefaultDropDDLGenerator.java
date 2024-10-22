package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityTable;

public final class DefaultDropDDLGenerator implements DropDDLGenerator {
    @Override
    public String generate(EntityTable entityTable) {
        String name = entityTable.tableName();

        return "DROP TABLE %s;".formatted(name);
    }
}
