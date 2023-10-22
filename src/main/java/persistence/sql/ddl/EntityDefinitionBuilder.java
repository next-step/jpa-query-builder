package persistence.sql.ddl;

import persistence.sql.dml.DataLanguage;
import utils.CustomStringBuilder;

public class EntityDefinitionBuilder {

    private final EntityMetadata entityMetadata;

    public EntityDefinitionBuilder(Class<?> type) {
        this.entityMetadata = new EntityMetadata(type);
    }

    public String create() {
        return new CustomStringBuilder()
                .append(DataLanguage.CREATE.getName())
                .append(entityMetadata.getTableName())
                .appendWithoutSpace(DataLanguage.LEFT_PARENTHESIS.getName())
                .appendWithoutSpace(entityMetadata.getColumnInfo())
                .appendWithoutSpace(DataLanguage.RIGHT_PARENTHESIS.getName())
                .appendWithoutSpace(DataLanguage.SEMICOLON.getName())
                .toString();
    }

    public String drop() {
        return  new CustomStringBuilder()
                .append(DataLanguage.DROP.getName())
                .appendWithoutSpace(entityMetadata.getTableName())
                .appendWithoutSpace(DataLanguage.SEMICOLON.getName())
                .toString();
    }

}
