package persistence.sql.ddl;

import persistence.sql.ddl.dialect.Dialect;

import static utils.CustomStringBuilder.toCreateStatement;
import static utils.CustomStringBuilder.toDropStatement;

public class EntityDefinitionBuilder {

    private EntityMetadata entityMetadata;

    public EntityDefinitionBuilder(EntityMetadata entityMetadata) {
        this.entityMetadata = entityMetadata;
    }

    public String create() {
        return toCreateStatement(entityMetadata.getTableName(), entityMetadata.getColumnInfo());
    }

    public String drop() {
        return toDropStatement(entityMetadata.getTableName());
    }

}
