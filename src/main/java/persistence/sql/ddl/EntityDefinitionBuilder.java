package persistence.sql.ddl;

import static utils.CustomStringBuilder.toCreateStatement;
import static utils.CustomStringBuilder.toDropStatement;

public class EntityDefinitionBuilder {

    private final EntityMetadata entityMetadata;

    public EntityDefinitionBuilder(Class<?> type) {
        this.entityMetadata = new EntityMetadata(type);
    }

    public String create() {
        return toCreateStatement(entityMetadata.getTableName(), entityMetadata.getColumnInfo());
    }

    public String drop() {
        return toDropStatement(entityMetadata.getTableName());
    }

}
