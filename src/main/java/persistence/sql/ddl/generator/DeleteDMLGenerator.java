package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public interface DeleteDMLGenerator {
    String generateDeleteAll(EntityFields entityFields);

    String generateDeleteById(EntityFields entityFields, Object id);
}
