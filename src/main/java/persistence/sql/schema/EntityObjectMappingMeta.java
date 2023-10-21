package persistence.sql.schema;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import persistence.sql.exception.AccessRequiredException;

public class EntityObjectMappingMeta {

    private static final String STRING_TYPE_FORMAT = "'%s'";

    private final EntityClassMappingMeta entityClassMappingMeta;
    private final Map<ColumnMeta, Object> objectValueMap = new LinkedHashMap<>();

    private EntityObjectMappingMeta(Map<ColumnMeta, Object> valueMap, EntityClassMappingMeta entityClassMappingMeta) {
        this.entityClassMappingMeta = entityClassMappingMeta;
        this.objectValueMap.putAll(valueMap);
    }

    public static EntityObjectMappingMeta of(Object instance, EntityClassMappingMeta entityClassMappingMeta) {
        final Map<ColumnMeta, Object> valueMap = new LinkedHashMap<>();

        entityClassMappingMeta.getMappingFieldList().forEach(field ->
            valueMap.put(entityClassMappingMeta.getColumnMeta(field), getFieldValueAsObject(field, instance))
        );

        return new EntityObjectMappingMeta(valueMap, entityClassMappingMeta);
    }

    public String getTableName() {
        return entityClassMappingMeta.tableClause();
    }

    public Set<ColumnMeta> getColumnMetaSet() {
        return objectValueMap.keySet();
    }

    public Map<String, Object> getValueMapByColumnName() {
        Map<String, Object> map = new HashMap<>();
        for (Entry<ColumnMeta, Object> entry : objectValueMap.entrySet()) {
            map.put(entry.getKey().getColumnName(), entry.getValue());
        }

        return map;
    }

    public String getFormattedValue(ColumnMeta columnMeta) {
        final Object value = objectValueMap.get(columnMeta);

        return formatValueAsString(value);
    }

    public static String formatValueAsString(Object object) {
        if (object == null) {
            return null;
        }

        if (object instanceof String) {
            return String.format(STRING_TYPE_FORMAT, object);
        }

        return String.valueOf(object);
    }

    private static Object getFieldValueAsObject(Field field, Object object) {
        field.setAccessible(true);
        try {
            return field.get(object);
        } catch (IllegalAccessException e) {
            throw new AccessRequiredException(e);
        }
    }
}
