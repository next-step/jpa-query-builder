package persistence.sql.dml.query.builder;

import persistence.sql.dml.query.clause.ColumnClause;
import persistence.sql.dml.query.clause.WhereClause;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.conditional.Criterias;

public class SelectQueryBuilder {
    private static final String FORMAT = "SELECT %s FROM %s %s";

    private final String tableName;
    private final ColumnClause columnClause;
    private final WhereClause whereClause;

    private SelectQueryBuilder(final String tableName,
                               final ColumnClause columnClause,
                               final WhereClause whereClause) {
        this.tableName = tableName;
        this.columnClause = columnClause;
        this.whereClause = whereClause;
    }

    public static SelectQueryBuilder of(final EntityMappingTable entityMappingTable,
                                        final Criterias criterias) {
        return new SelectQueryBuilder(
                entityMappingTable.getTableName(),
                ColumnClause.from(entityMappingTable.getDomainTypes()),
                new WhereClause(criterias)
        );
    }

    public static SelectQueryBuilder from(final EntityMappingTable entityMappingTable) {
        return new SelectQueryBuilder(
                entityMappingTable.getTableName(),
                ColumnClause.from(entityMappingTable.getDomainTypes()),
                new WhereClause(Criterias.emptyInstance())
        );
    }

    public String toSql() {
        return String.format(FORMAT, columnClause.toSql(), tableName, whereClause.toSql());
    }


}
