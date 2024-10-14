package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public interface SelectDMLGenerator {
    String generateFindAll(EntityFields entityFields);

    String generateFindById(EntityFields entityFields, Object id);
}
