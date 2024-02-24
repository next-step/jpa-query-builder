package database.sql.dml;

import database.sql.util.EntityClassInspector;
import database.sql.util.column.EntityColumn;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueMap {

    private final Map<String, Object> values;
    private final EntityClassInspector entityClassInspector;

    public ValueMap(Object entity) {
        entityClassInspector = new EntityClassInspector(entity);
        values = extractValues(entity);
    }

    private Map<String, Object> extractValues(Object entity) {
        List<EntityColumn> generalColumns = entityClassInspector.getColumnsForInserting();

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

    public Map<String, Object> getValuesMap() {
        return values;
    }
}
