package persistence.meta.vo;

import jakarta.persistence.GeneratedValue;

public class EntityId {
    private final EntityField entityField;
    private final GeneratedValue generatedValue;

    public EntityId(EntityField entityField,
                    GeneratedValue generatedValue) {
        this.entityField = entityField;
        this.generatedValue = generatedValue;
    }


    public EntityField getEntityField() {
        return entityField;
    }

    public GeneratedValue getGeneratedValue() {
        return generatedValue;
    }

    public String getTableFieldName() {
        return entityField.getTableFieldName();
    }
}
