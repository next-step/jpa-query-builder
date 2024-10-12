package persistence.sql.ddl;

import persistence.sql.ddl.node.EntityNode;

public interface QueryBuilder {
    String buildCreateTableQuery(EntityNode<?> entityNode);
}
