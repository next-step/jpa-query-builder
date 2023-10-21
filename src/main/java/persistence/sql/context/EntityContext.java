package persistence.sql.context;

import persistence.sql.attribute.GeneralAttribute;
import persistence.sql.attribute.id.IdAttribute;
import persistence.sql.parser.ValueParser;

import java.util.List;
import java.util.Map;

public class EntityContext {
    private final String tableName;
    private final Map.Entry<IdAttribute, String> IdAttributeWithValuePair;
    private final Map<GeneralAttribute, String> AttributeWithValueMap;

    private EntityContext(
            Map.Entry<IdAttribute, String> idAttributeWithValuePair,
            Map<GeneralAttribute, String> attributeWithValueMap,
            String tableName) {
        this.IdAttributeWithValuePair = idAttributeWithValuePair;
        this.AttributeWithValueMap = attributeWithValueMap;
        this.tableName = tableName;
    }

    public static <T> EntityContext of(
            String tableName,
            IdAttribute idAttribute,
            List<GeneralAttribute> generalAttributes,
            ValueParser valueParser,
            T instance
    ) throws NoSuchFieldException, IllegalAccessException {
        Map.Entry<IdAttribute, String> idAttributeWithValuePair = valueParser.parseIdAttributeWithValuePair(idAttribute, instance);

        Map<GeneralAttribute, String> attributeWithValueMap = valueParser.parseAttributeWithValueMap(generalAttributes, instance);

        return new EntityContext(idAttributeWithValuePair, attributeWithValueMap, tableName);
    }

    public Map.Entry<IdAttribute, String> getIdAttributeWithValuePair() {
        return IdAttributeWithValuePair;
    }

    public Map<GeneralAttribute, String> getAttributeWithValueMap() {
        return AttributeWithValueMap;
    }

    public String getTableName() {
        return tableName;
    }
}
