package persistence.sql.ddl;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class CreateQueryBuilder implements QueryBuilder {
    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE %s";

    private final EntityMetadata entityMetadata;

    public CreateQueryBuilder(Class<?> clazz) {
        this.entityMetadata = new EntityMetadata(clazz);
    }

    @Override
    public String buildQuery() {
        return format(CREATE_TABLE_COMMAND, entityMetadata.getTableName()) +
                "(" +
                entityMetadata.getColumnsToCreate() +
                ");";
    }
}
