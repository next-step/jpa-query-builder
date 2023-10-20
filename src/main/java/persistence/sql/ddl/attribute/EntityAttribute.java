package persistence.sql.ddl.attribute;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import persistence.sql.ddl.attribute.id.IdAttribute;
import persistence.sql.ddl.parser.AttributeParser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class EntityAttribute {
    private final String tableName;
    private final List<GeneralAttribute> generalAttributes;
    private final IdAttribute idAttribute;

    private EntityAttribute(
            String tableName,
            IdAttribute idAttribute,
            List<GeneralAttribute> generalAttributes
    ) {
        this.tableName = tableName;
        this.generalAttributes = generalAttributes;
        this.idAttribute = idAttribute;
    }

    public static EntityAttribute of(Class<?> clazz, AttributeParser parser) {
        validate(clazz);

        String tableName = Optional.ofNullable(clazz.getAnnotation(Table.class)).map(Table::name).orElse(clazz.getSimpleName());

        List<GeneralAttribute> generalAttributes = parser.parseGeneralAttributes(clazz);
        IdAttribute idAttribute = parser.parseIdAttribute(clazz);

        return new EntityAttribute(tableName, idAttribute, generalAttributes);
    }

    private static void validate(Class<?> clazz) {
        long idAnnotatedFieldCount = Arrays.stream((clazz.getDeclaredFields()))
                .filter(it -> it.isAnnotationPresent(Id.class))
                .count();

        if (idAnnotatedFieldCount != 1) {
            throw new IllegalStateException(String.format("[%s] @Id 어노테이션이 1개가 아닙니다.", clazz.getSimpleName()));
        }
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new IllegalStateException(String.format("[%s] @Entity 어노테이션이 없습니다.", clazz.getSimpleName()));

        }
    }

    public String getTableName() {
        return tableName;
    }

    public String getAttributeComponents() {
        return idAttribute.makeComponent() + ", " + generalAttributes.stream()
                .map(GeneralAttribute::makeComponent)
                .collect(Collectors.joining(", "));
    }
}
