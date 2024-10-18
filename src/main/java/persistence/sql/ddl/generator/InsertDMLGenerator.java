package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public interface InsertDMLGenerator {
    String generateInsert(Object object);

    String generateGetLastKey(EntityFields entityFields);
}
