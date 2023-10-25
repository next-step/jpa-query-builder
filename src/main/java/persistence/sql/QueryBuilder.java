package persistence.sql;

import persistence.sql.metadata.EntityMetadata;

public interface QueryBuilder {
    String buildQuery(EntityMetadata entityMetadata);
}
