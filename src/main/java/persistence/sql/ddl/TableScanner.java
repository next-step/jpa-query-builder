package persistence.sql.ddl;

import persistence.sql.ddl.node.EntityNode;

import java.util.Set;

public interface TableScanner {
    Set<EntityNode<?>> scan(String basePackage);
}
