package persistence.sql.ddl.parser;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import persistence.sql.ddl.attribute.GeneralAttribute;
import persistence.sql.ddl.attribute.IntegerTypeGeneralAttribute;
import persistence.sql.ddl.attribute.LongTypeGeneralAttribute;
import persistence.sql.ddl.attribute.StringTypeGeneralAttribute;
import persistence.sql.ddl.attribute.id.IdAttribute;
import persistence.sql.ddl.attribute.id.LongTypeIdAttribute;
import persistence.sql.ddl.attribute.id.StringTypeIdAttribute;
import persistence.sql.ddl.converter.SqlConverter;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class AttributeParser {
    private final SqlConverter sqlConverter;

    public AttributeParser(SqlConverter sqlConverter) {
        this.sqlConverter = sqlConverter;
    }

    private static IdAttribute makeIdAttribute(Field field, SqlConverter sqlConverter) {
        if (field.getType().equals(String.class)) {
            return StringTypeIdAttribute.of(field, sqlConverter);
        }
        if (field.getType().equals(Long.class)) {
            return LongTypeIdAttribute.of(field, sqlConverter);
        }
        throw new IllegalStateException(String.format("[%s] 알수없는 필드 타입입니다.", field.getType()));
    }

    public IdAttribute parseIdAttribute(Class<?> clazz) {
        Field id = Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Id.class))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("[%s] 엔티티에 @Id가 없습니다", clazz.getSimpleName())));
        return makeIdAttribute(id, sqlConverter);
    }

    public List<GeneralAttribute> parseGeneralAttributes(Class<?> clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                .filter(field -> field.isAnnotationPresent(Column.class)
                        && !field.isAnnotationPresent(Id.class))
                .map(field -> makeGeneralAttribute(field, sqlConverter))
                .collect(Collectors.toList());
    }

    private GeneralAttribute makeGeneralAttribute(
            Field field,
            SqlConverter sqlConverter
    ) {
        if (field.getType().equals(String.class)) {
            return StringTypeGeneralAttribute.of(field, sqlConverter);
        }
        if (field.getType().equals(Integer.class)) {
            return IntegerTypeGeneralAttribute.of(field, sqlConverter);
        }
        if (field.getType().equals(Long.class)) {
            return LongTypeGeneralAttribute.of(field, sqlConverter);
        }
        throw new IllegalStateException(String.format("[%s] 필드의 타입을 알 수 없습니다.", field.getName()));
    }
}
