package persistence.meta.vo;

public class EntityId {
    private final String originalFieldName;
    private final Class<?> fieldType;
    private final EntityField entityField;
    private final boolean isPrimary = true;

    public EntityId(String originalFieldName, Class<?> fieldType, EntityField entityField) {
        this.originalFieldName = originalFieldName;
        this.fieldType = fieldType;
        this.entityField = entityField;
    }

    public String getOriginalFieldName() {
        return originalFieldName;
    }

    public Class<?> getFieldType() {
        return fieldType;
    }

    public EntityField getEntityField() {
        return entityField;
    }

    public boolean isPrimary() {
        return isPrimary;
    }
}
