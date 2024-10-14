package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public interface CreateDDLGenerator {
    String generate(EntityFields entityFields);
}
