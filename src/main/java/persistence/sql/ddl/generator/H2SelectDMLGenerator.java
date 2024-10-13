package persistence.sql.ddl.generator;

import persistence.sql.ddl.EntityFields;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class H2SelectDMLGenerator implements SelectDMLGenerator{
    @Override
    public String generateFindAll(EntityFields entityFields) {
        return "select * from %s;".formatted(entityFields.tableName());
    }

    @Override
    public String generateFindById(EntityFields entityFields, Object id) {
        return "select * from %s where %s = %s;".formatted(entityFields.tableName(), entityFields.getIdFieldName(), id);
    }
}
