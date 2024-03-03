package persistence.meta.vo;

import jakarta.persistence.GeneratedValue;

public class EntityId {
    private final String originalFieldName;
    private final EntityField entityField;
    private final GeneratedValue generatedValue;

    public EntityId(String originalFieldName, EntityField entityField,
                    GeneratedValue generatedValue) {
        this.originalFieldName = originalFieldName;
        this.entityField = entityField;
        this.generatedValue = generatedValue;
    }

    public String getOriginalFieldName() {
        return originalFieldName;
    }

    public EntityField getEntityField() {
        return entityField;
    }

    public GeneratedValue getGeneratedValue() {
        return generatedValue;
    }
}
