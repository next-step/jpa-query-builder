package persistence.sql.meta;

import jakarta.persistence.Entity;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EntityTable {
    public static final String NOT_ENTITY_FAILED_MESSAGE = "클래스에 @Entity 애노테이션이 없습니다.";
    public static final String NOT_ID_FAILED_MESSAGE = "필드에 @Id 애노테이션이 없습니다.";

    private final Class<?> entityClass;
    private final EntityFields entityFields;

    public EntityTable(Class<?> entityClass) {
        if (!entityClass.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(NOT_ENTITY_FAILED_MESSAGE);
        }

        this.entityClass = entityClass;
        this.entityFields = new EntityFields(entityClass);
    }

    public List<EntityField> getEntityFields() {
        return entityFields.getEntityFields();
    }

    public String getQuery(String queryTemplate, String... templateArgs) {
        return String.format(queryTemplate, (Object[]) templateArgs);
    }

    public String getTableName() {
        final jakarta.persistence.Table table = entityClass.getAnnotation(jakarta.persistence.Table.class);
        if (Objects.nonNull(table) && Objects.nonNull(table.name()) && !table.name().isBlank()) {
            return table.name();
        }
        return entityClass.getSimpleName()
                .toLowerCase();
    }

    public String getWhereClause(Object id) {
        final EntityField entityField = new EntityField(getIdField());
        return entityField.getColumnName() + " = " + id;
    }

    private Field getIdField() {
        return Arrays.stream(entityClass.getDeclaredFields())
                .filter(this::isId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(NOT_ID_FAILED_MESSAGE));
    }

    private boolean isId(Field field) {
        return new EntityField(field).isId();
    }
}
