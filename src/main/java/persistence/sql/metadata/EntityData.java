package persistence.sql.metadata;

import java.util.List;
import java.util.stream.Collectors;

public class EntityData {
    private final EntityMetadata entityMetadata;
    private final List<ColumnData> columnDataList;

    private EntityData(EntityMetadata entityMetadata, List<ColumnData> columnDataList) {
        this.entityMetadata = entityMetadata;
        this.columnDataList = columnDataList;
    }

    public static EntityData from(Object entity) {
        EntityMetadata entityMetadata = EntityMetadata.from(entity.getClass());
        List<ColumnData> dataList = entityMetadata.getColumnMetadata().withData(entity);

        return new EntityData(entityMetadata, dataList);
    }

    public String getTableName() {
        return entityMetadata.getTableName();
    }

    public ColumnData getPrimaryKey() {
        return columnDataList.stream()
                .filter(ColumnData::isPrimaryKey)
                .findFirst()
                .orElseThrow();
    }

    public ColumnDatas getInsertColumns() {
        return columnDataList.stream()
                .filter(ColumnData::hasNotIdentityStrategy)
                .collect(Collectors.collectingAndThen(Collectors.toList(), ColumnDatas::new));
    }
}
