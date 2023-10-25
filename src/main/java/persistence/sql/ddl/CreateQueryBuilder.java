package persistence.sql.ddl;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class CreateQueryBuilder implements QueryBuilder {
    private static final String CREATE_TABLE_COMMAND = "CREATE TABLE %s";

    public CreateQueryBuilder() {
    }

    @Override
    public String buildQuery(EntityMetadata entityMetadata) {
        return format(CREATE_TABLE_COMMAND, entityMetadata.getTableName()) +
                "(" +
                entityMetadata.getColumnsToCreate() +
                ");";
    }
}
