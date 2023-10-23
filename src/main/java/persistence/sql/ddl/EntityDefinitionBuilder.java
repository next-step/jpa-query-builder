package persistence.sql.ddl;

import utils.CustomStringBuilder;

import static persistence.sql.dml.DataLanguage.*;

public class EntityDefinitionBuilder {

    private final EntityMetadata entityMetadata;

    public EntityDefinitionBuilder(Class<?> type) {
        this.entityMetadata = new EntityMetadata(type);
    }

    public String create() {
        return new CustomStringBuilder()
                .append(CREATE.getName())
                .append(entityMetadata.getTableName())
                .appendWithoutSpace(LEFT_PARENTHESIS.getName())
                .appendWithoutSpace(entityMetadata.getColumnInfo())
                .appendWithoutSpace(RIGHT_PARENTHESIS.getName())
                .appendWithoutSpace(SEMICOLON.getName())
                .toString();
    }

    public String drop() {
        return  new CustomStringBuilder()
                .append(DROP.getName())
                .appendWithoutSpace(entityMetadata.getTableName())
                .appendWithoutSpace(SEMICOLON.getName())
                .toString();
    }

}
