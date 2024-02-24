package persistence.sql.dml.query.builder;

import persistence.sql.dml.query.clause.ColumnClause;
import persistence.sql.entity.EntityMappingTable;

public class SelectQueryBuilder {
    private static final String FORMAT = "SELECT %s FROM %s";

    private final String tableName;
    private final ColumnClause columnClause;

    private SelectQueryBuilder(final String tableName,
                               final ColumnClause columnClause) {
        this.tableName = tableName;
        this.columnClause = columnClause;
    }

    public String toSql() {
        return String.format(FORMAT, columnClause.toSql(), tableName);
    }

    public static SelectQueryBuilder from(EntityMappingTable entityMappingTable) {
        return new SelectQueryBuilder(
                entityMappingTable.getTableName(),
                ColumnClause.from(entityMappingTable.getDomainTypes())
        );
    }


}
