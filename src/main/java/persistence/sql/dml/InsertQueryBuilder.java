package persistence.sql.dml;

import persistence.core.EntityMetadataModel;

public interface InsertQueryBuilder {

    String createInsertQuery(EntityMetadataModel entityMetadataModel, Object entity);
}
