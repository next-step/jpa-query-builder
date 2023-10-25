package persistence.sql.ddl;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class DropQueryBuilder implements QueryBuilder {
    private static final String DROP_TABLE_COMMAND = "DROP TABLE %s IF EXISTS;";

    public DropQueryBuilder() {
    }

    @Override
    public String buildQuery(EntityMetadata entityMetadata) {
        return format(DROP_TABLE_COMMAND, entityMetadata.getTableName());
    }
}
