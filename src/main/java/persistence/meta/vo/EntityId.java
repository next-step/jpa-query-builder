package persistence.meta.vo;

import jakarta.persistence.GeneratedValue;

public class EntityId {
    private final String originalFieldName;
    private final Class<?> fieldType;
    private final EntityField entityField;
    private final GeneratedValue generatedValue;

    public EntityId(String originalFieldName, Class<?> fieldType, EntityField entityField,
                    GeneratedValue generatedValue) {
        this.originalFieldName = originalFieldName;
        this.fieldType = fieldType;
        this.entityField = entityField;
        this.generatedValue = generatedValue;
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

    public GeneratedValue getGeneratedValue() {
        return generatedValue;
    }
}
