package persistence.sql.dml.query.builder;

import persistence.sql.dml.query.clause.ColumnClause;
import persistence.sql.dml.query.clause.WhereClause;
import persistence.sql.entity.EntityMappingTable;
import persistence.sql.entity.model.DomainType;

import java.util.HashMap;
import java.util.Map;

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

    public String toSql() {
        return String.format(FORMAT, columnClause.toSql(), tableName, whereClause.toSql());
    }

    public static SelectQueryBuilder of(EntityMappingTable entityMappingTable, Map<DomainType, String> whereClause) {
        return new SelectQueryBuilder(
                entityMappingTable.getTableName(),
                ColumnClause.from(entityMappingTable.getDomainTypes()),
                new WhereClause(whereClause)
        );
    }

    public static SelectQueryBuilder from(EntityMappingTable entityMappingTable) {
        return new SelectQueryBuilder(
                entityMappingTable.getTableName(),
                ColumnClause.from(entityMappingTable.getDomainTypes()),
                new WhereClause(new HashMap())
        );
    }


}
