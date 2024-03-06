package persistence.meta.vo;

import java.util.List;

public class EntityMetaData {
    private final Class<?> entityClass;
    private final EntityId entityId;
    private final TableName tableName;
    private final List<EntityField> entityFields;

    public static EntityMetaData createFromClass(Class<?> cls) {
        return new EntityMetaData(
            cls,
            EntityId.createFromClass(cls),
            TableName.createFromClass(cls),
            EntityField.createFromClass(cls)
        );
    }

    private EntityMetaData(Class<?> entityClass, EntityId entityId, TableName tableName,
                           List<EntityField> entityFields) {
        this.entityClass = entityClass;
        this.entityId = entityId;
        this.tableName = tableName;
        this.entityFields = entityFields;
    }

    public TableName getTableName() {
        return tableName;
    }

    public List<EntityField> getEntityFields() {
        return entityFields;
    }

    public EntityId getEntityId() {
        return entityId;
    }
}
