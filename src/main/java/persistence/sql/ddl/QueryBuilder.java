package persistence.sql.ddl;

import persistence.sql.ddl.node.EntityNode;
import persistence.sql.ddl.node.FieldNode;

import java.util.List;

public interface QueryBuilder {
    <T> String buildCreateTableQuery(EntityNode<T> entityNode);

    String buildPrimaryKeyQuery(List<FieldNode> fieldNodes);

    String buildColumnQuery(FieldNode fieldNode);

    String buildConstraintQuery(FieldNode fieldNode);
}
