package persistence.sql.meta;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class EntityTable {
    public static final String NOT_ENTITY_FAILED_MESSAGE = "클래스에 @Entity 애노테이션이 없습니다.";
    public static final String NOT_ID_FAILED_MESSAGE = "필드에 @Id 애노테이션이 없습니다.";

    private final Class<?> entityType;
    private final EntityFields entityFields;

    public EntityTable(Class<?> entityType) {
        if (!entityType.isAnnotationPresent(Entity.class)) {
            throw new IllegalArgumentException(NOT_ENTITY_FAILED_MESSAGE);
        }

        this.entityType = entityType;
        this.entityFields = new EntityFields(entityType);
    }

    public List<EntityField> getEntityFields() {
        return entityFields.getEntityFields();
    }

    public String getQuery(String queryTemplate, String... templateArgs) {
        return String.format(queryTemplate, (Object[]) templateArgs);
    }

    public String getTableName() {
        final Table table = entityType.getAnnotation(Table.class);
        if (Objects.nonNull(table) && Objects.nonNull(table.name()) && !table.name().isBlank()) {
            return table.name();
        }
        return entityType.getSimpleName()
                .toLowerCase();
    }

    public String getWhereClause(Object id) {
        final EntityField entityField = getIdEntityField();
        return entityField.getColumnName() + " = " + id;
    }

    public Object getIdValue(Object entity) {
        final EntityField entityField = getIdEntityField();
        return entityField.getValue(entity);
    }

    private EntityField getIdEntityField() {
        final Field field = Arrays.stream(entityType.getDeclaredFields())
                .filter(this::isId)
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(NOT_ID_FAILED_MESSAGE));
        return new EntityField(field);
    }

    private boolean isId(Field field) {
        return new EntityField(field).isId();
    }
}
