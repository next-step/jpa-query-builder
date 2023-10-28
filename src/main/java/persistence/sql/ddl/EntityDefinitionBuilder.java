package persistence.sql.ddl;

import persistence.sql.ddl.dialect.Dialect;

import static utils.CustomStringBuilder.toCreateStatement;
import static utils.CustomStringBuilder.toDropStatement;

public class EntityDefinitionBuilder {

    // TODO List가 되어야 한다.
    private EntityMetadata entityMetadata;

    // TODO 패키지 받아서 정보 읽어와서 EntityMetadata 초기화
    public EntityDefinitionBuilder() {
    }

    public EntityDefinitionBuilder(Class<?> type, Dialect dialectParam) {
        this.entityMetadata = new EntityMetadata(type, dialectParam);
    }

    public String create() {
        return toCreateStatement(entityMetadata.getTableName(), entityMetadata.getColumnInfo());
    }

    public String drop() {
        return toDropStatement(entityMetadata.getTableName());
    }

}
