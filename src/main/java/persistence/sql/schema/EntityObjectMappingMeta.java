package persistence.sql.schema;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import persistence.sql.exception.AccessRequiredException;

public class EntityObjectMappingMeta {

    private final Map<ColumnMeta, ValueMeta> objectValueMap = new LinkedHashMap<>();

    private EntityObjectMappingMeta(Map<ColumnMeta, ValueMeta> valueMap) {
        this.objectValueMap.putAll(valueMap);
    }

    public static EntityObjectMappingMeta of(Object instance, EntityClassMappingMeta entityClassMappingMeta) {
        final Map<ColumnMeta, ValueMeta> valueMap = new LinkedHashMap<>();

        entityClassMappingMeta.getMappingFieldList().forEach(field ->
            valueMap.put(entityClassMappingMeta.getColumnMeta(field), getFieldValueAsObject(field, instance))
        );

        return new EntityObjectMappingMeta(valueMap);
    }

    public List<ColumnMeta> getColumnMetaList() {
        return new ArrayList<>(objectValueMap.keySet());
    }

    public List<Entry<ColumnMeta, ValueMeta>> getMetaEntryList() {
        return new ArrayList<>(objectValueMap.entrySet());
    }

    public Map<String, Object> getValueMapByColumnName() {
        Map<String, Object> map = new HashMap<>();
        for (Entry<ColumnMeta, ValueMeta> entry : objectValueMap.entrySet()) {
            map.put(entry.getKey().getColumnName(), entry.getValue().getValue());
        }

        return map;
    }

    private static ValueMeta getFieldValueAsObject(Field field, Object object) {
        field.setAccessible(true);
        try {
            return ValueMeta.of(field.get(object));
        } catch (IllegalAccessException e) {
            throw new AccessRequiredException(e);
        }
    }
}
