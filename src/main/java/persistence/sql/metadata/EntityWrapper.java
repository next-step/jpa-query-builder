package persistence.sql.metadata;

public class EntityWrapper {
    private final EntityMetadata entityMetadata;
    private final EntityData entityData;

    private EntityWrapper(EntityMetadata entityMetadata, EntityData entityData) {
        this.entityMetadata = entityMetadata;
        this.entityData = entityData;
    }

    public static EntityWrapper from(Object entity) {
        EntityMetadata entityMetadata = EntityMetadata.from(entity.getClass());
        EntityData entityData = entityMetadata.getColumnMetadata().withData(entity);
        return new EntityWrapper(entityMetadata, entityData);
    }

    public String getTableName() {
        return entityMetadata.getTableName();
    }

    public ColumnData getPrimaryKey() {
        return entityData.getPrimaryKey();
    }

    public EntityData getInsertColumns() {
        return entityData.getInsertColumns();
    }

    public EntityData getColumns() {
        return entityData;
    }
}
