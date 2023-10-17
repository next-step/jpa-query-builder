package persistence.sql.ddl;

import persistence.core.EntityMetadataModel;

public interface TableDdlQueryBuilder {

    String createDdlQuery(EntityMetadataModel entityMetadataModel);

    String createDropTableQuery(EntityMetadataModel entityMetadataModel);

    default String getNullableConstraintQuery(boolean isNullable) {
        return isNullable ? "null" : "not null";
    }

    default String getUniqueConstraintQuery(boolean isUnique) {
        return isUnique ? "unique" : "";
    }
}
