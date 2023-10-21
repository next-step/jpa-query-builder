package persistence.sql.schema;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

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

    public ColumnMeta getIdColumnMeta() {
        return entityClassMappingMeta.getIdFieldColumnMeta();
    }

    public String getValueAsString(ColumnMeta columnMeta) {
        final Object value = objectValueMap.get(columnMeta);

        return formatValueAsString(value);
    }

    public static String formatValueAsString(Object object) {
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
            throw new RuntimeException(e);
        }
    }
}
