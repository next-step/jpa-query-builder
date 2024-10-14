package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public class DefaultDeleteDMLGenerator implements DeleteDMLGenerator {
    @Override
    public String generateDeleteAll(EntityFields entityFields) {
        return "delete from %s;".formatted(entityFields.tableName());
    }

    @Override
    public String generateDeleteById(EntityFields entityFields, Object id) {
        return "delete from %s where %s = %s;".formatted(entityFields.tableName(), entityFields.getIdFieldName(), id);
    }
}
