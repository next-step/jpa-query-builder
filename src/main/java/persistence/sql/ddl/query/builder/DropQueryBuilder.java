package persistence.sql.ddl.query.builder;

import persistence.sql.ddl.query.EntityMappingTable;

public class DropQueryBuilder {

    private static final String DROP_SQL = "DROP TABLE IF EXISTS %s;";

    private final EntityMappingTable entityMappingTable;

    public DropQueryBuilder(final EntityMappingTable entityMappingTable) {
        this.entityMappingTable = entityMappingTable;
    }

    public String toSql() {
        return String.format(DROP_SQL, entityMappingTable.getTableName());
    }

}
