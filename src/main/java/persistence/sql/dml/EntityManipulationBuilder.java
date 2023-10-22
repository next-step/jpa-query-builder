package persistence.sql.dml;

import persistence.sql.ddl.EntityMetadata;
import utils.CustomStringBuilder;

public class EntityManipulationBuilder {

    private final EntityMetadata entityMetadata;

    public EntityManipulationBuilder(Class<?> type) {
        this.entityMetadata = new EntityMetadata(type);
    }

    public String insert() {

        return new CustomStringBuilder()
                .append(DataLanguage.INSERT.getName())
                .append(entityMetadata.getTableName())
                .appendWithoutSpace(DataLanguage.LEFT_PARENTHESIS.getName())





        return "INSERT INTO users (nick_name, old, email) " +
                "VALUES ('John Doe', 30, 'john.doe@example.com');";
    }

    private String columnsClause(Class<?> clazz) {
        return "users (nick_name, old, email)";
    }

    private String valueClause(Object object) {
        return "('John Doe', 30, 'john.doe@example.com')";
    }

}
