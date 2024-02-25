package database.sql.dml;

import database.sql.util.EntityMetadata;
import database.sql.util.column.EntityColumn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueMap {

    private final Map<String, Object> valuesMap;

    public ValueMap(Object entity) {
        valuesMap = extractValues(entity);
    }

    public Map<String, Object> getValuesMap() {
        return valuesMap;
    }

    private Map<String, Object> extractValues(Object entity) {
        EntityMetadata entityMetadata = new EntityMetadata(entity);
        List<EntityColumn> generalColumns = entityMetadata.getGeneralColumns();

        Map<String, Object> map = new HashMap<>();
        try {
            for (EntityColumn column : generalColumns) {
                Object value = column.getValue(entity);
                map.put(column.getColumnName(), value);
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return map;
    }
}
