package persistence.sql.metadata;

public class EntityData {
    private final EntityMetadata entityMetadata;
    private final ColumnDatas columnDatas;

    private EntityData(EntityMetadata entityMetadata, ColumnDatas columnDatas) {
        this.entityMetadata = entityMetadata;
        this.columnDatas = columnDatas;
    }

    public static EntityData from(Object entity) {
        EntityMetadata entityMetadata = EntityMetadata.from(entity.getClass());
        ColumnDatas columnDatas = entityMetadata.getColumnMetadata().withData(entity);
        return new EntityData(entityMetadata, columnDatas);
    }

    public String getTableName() {
        return entityMetadata.getTableName();
    }

    public ColumnData getPrimaryKey() {
        return columnDatas.getPrimaryKey();
    }

    public ColumnDatas getInsertColumns() {
        return columnDatas.getInsertColumns();
    }

    public ColumnDatas getColumns() {
        return columnDatas;
    }
}
