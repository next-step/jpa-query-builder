package persistence.sql.attribute;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import persistence.sql.attribute.id.IdAttribute;
import persistence.sql.context.EntityContext;
import persistence.sql.ddl.wrapper.DDLWrapper;
import persistence.sql.parser.AttributeParser;
import persistence.sql.parser.ValueParser;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    public <T> EntityContext createEntityContext(T instance) {
        try {
            ValueParser valueParser = new ValueParser();
            return EntityContext.of(
                    this.tableName,
                    this.idAttribute,
                    this.generalAttributes,
                    valueParser,
                    instance
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String prepareDDL(DDLWrapper ddlWrapper) {
        return ddlWrapper.wrap(tableName, idAttribute, generalAttributes);
    }

    public String getTableName() {
        return tableName;
    }
}
