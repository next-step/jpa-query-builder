package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public final class H2DropDDLGenerator implements DropDDLGenerator {
    @Override
    public String generate(EntityFields entityFields) {
        String name = entityFields.tableName();

        return "DROP TABLE %s;".formatted(name);
    }
}