package persistence.meta;

import java.util.List;

public class EntityMetaData {
    private final Class<?> entityClass;
    private final EntityId entityId;
    private final List<EntityField> entityFields;

    public EntityMetaData(Class<?> entityClass, EntityId entityId, List<EntityField> entityFields) {
        this.entityClass = entityClass;
        this.entityId = entityId;
        this.entityFields = entityFields;
    }
}
