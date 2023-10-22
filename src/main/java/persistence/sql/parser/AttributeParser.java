package persistence.sql.parser;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.entitiy.attribute.GeneralAttribute;
import persistence.entitiy.attribute.IntegerTypeGeneralAttribute;
import persistence.entitiy.attribute.LongTypeGeneralAttribute;
import persistence.entitiy.attribute.StringTypeGeneralAttribute;
import persistence.entitiy.attribute.id.IdAttribute;
import persistence.entitiy.attribute.id.LongTypeIdAttribute;
import persistence.entitiy.attribute.id.StringTypeIdAttribute;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AttributeParser {

    public AttributeParser() {
    }

    private static IdAttribute createIdAttribute(Field field) {
        if (field.getType().equals(String.class)) {
            return StringTypeIdAttribute.of(field);
        }
        if (field.getType().equals(Long.class)) {
            return LongTypeIdAttribute.of(field);
        }
        throw new IllegalStateException(String.format("[%s] 알수없는 필드 타입입니다.", field.getType()));
    }

    public IdAttribute parseIdAttribute(Class<?> clazz) {
        Field id = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("[%s] 엔티티에 @Id가 없습니다", clazz.getSimpleName())));
        return createIdAttribute(id);
    }

    public List<GeneralAttribute> parseGeneralAttributes(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class)
                        && !field.isAnnotationPresent(Id.class))
                .map(this::makeGeneralAttribute)
                .collect(Collectors.toList());
    }

    private GeneralAttribute makeGeneralAttribute(
            Field field
    ) {
        if (field.getType().equals(String.class)) {
            return StringTypeGeneralAttribute.of(field);
        }
        if (field.getType().equals(Integer.class)) {
            return IntegerTypeGeneralAttribute.of(field);
        }
        if (field.getType().equals(Long.class)) {
            return LongTypeGeneralAttribute.of(field);
        }
        throw new IllegalStateException(String.format("[%s] 필드의 타입을 알 수 없습니다.", field.getName()));
    }
}
