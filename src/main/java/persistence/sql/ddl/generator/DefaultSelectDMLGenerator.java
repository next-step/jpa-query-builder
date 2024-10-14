package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

public class DefaultSelectDMLGenerator implements SelectDMLGenerator {
    @Override
    public String generateFindAll(EntityFields entityFields) {
        return "select * from %s;".formatted(entityFields.tableName());
    }

    @Override
    public String generateFindById(EntityFields entityFields, Object id) {
        return "select * from %s where %s = %s;".formatted(entityFields.tableName(), entityFields.getIdFieldName(), id);
    }
}
