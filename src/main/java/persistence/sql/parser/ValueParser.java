package persistence.sql.parser;

import persistence.entitiy.attribute.GeneralAttribute;
import persistence.entitiy.attribute.id.IdAttribute;
import persistence.exception.ParseAttributeException;

import java.lang.reflect.Field;
import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ValueParser {
    public <T> Map.Entry<IdAttribute, String> parseIdAttributeWithValuePair(
            IdAttribute idAttribute, T instance) {
        try {
            Field idField = instance.getClass().getDeclaredField(idAttribute.getFieldName());
            idField.setAccessible(true);
            return new AbstractMap.SimpleEntry<>(idAttribute,
                    idField.get(instance) == null ? null : String.valueOf(idField.get(instance)));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ParseAttributeException("ID 어트리뷰트 파싱 중 에러 발생", e);
        }
    }

    public <T> Map<GeneralAttribute, String> parseAttributeWithValueMap(List<GeneralAttribute> generalAttributes, T instance) {
        return generalAttributes.stream()
                .map(generalAttribute -> {
                    String value = getAttributeValue(instance, generalAttribute);
                    return new AbstractMap.SimpleEntry<>(generalAttribute, value);
                })
                .filter(entry -> entry.getValue() != null)
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    private <T> String getAttributeValue(T instance, GeneralAttribute generalAttribute) {
        try {
            Field generalField = instance.getClass().getDeclaredField(generalAttribute.getFieldName());
            generalField.setAccessible(true);
            return generalField.get(instance) == null ? null : String.valueOf(generalField.get(instance));
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new ParseAttributeException("일반 어트리뷰트 파싱 중 에러 발생", e);
        }
    }
}
