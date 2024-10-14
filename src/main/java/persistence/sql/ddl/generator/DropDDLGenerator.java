package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public interface DropDDLGenerator {
    String generate(EntityFields entityFields);
}
