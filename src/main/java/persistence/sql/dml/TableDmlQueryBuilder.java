package persistence.sql.dml;

import persistence.core.EntityMetadataModel;

public interface TableDmlQueryBuilder {

    String createInsertQuery(EntityMetadataModel entityMetadataModel, Object entity);
}
