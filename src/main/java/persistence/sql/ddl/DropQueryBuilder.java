package persistence.sql.ddl;

import persistence.sql.metadata.EntityMetadata;
import persistence.sql.QueryBuilder;

import static java.lang.String.format;

public class DropQueryBuilder implements QueryBuilder {
    private static final String DROP_TABLE_COMMAND = "DROP TABLE %s IF EXISTS;";
    private final EntityMetadata entityMetadata;

    public DropQueryBuilder(Class<?> clazz) {
        this.entityMetadata = new EntityMetadata(clazz);
    }

    @Override
    public String buildQuery() {
        return format(DROP_TABLE_COMMAND, entityMetadata.getTableName());
    }
}
