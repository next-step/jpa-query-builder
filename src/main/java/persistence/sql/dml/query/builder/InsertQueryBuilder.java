package persistence.sql.dml.query.builder;

import persistence.sql.dml.query.clause.ColumnClause;
import persistence.sql.dml.query.clause.ValueClause;
import persistence.sql.entity.EntityMappingTable;

public class InsertQueryBuilder {

    private static final String FORMAT = "INSERT INTO %s (%s) VALUES (%s)";

    private final String tableName;
    private final ColumnClause columnClause;
    private final ValueClause valueClause;

    private InsertQueryBuilder(final String tableName,
                               final ColumnClause columnClause,
                               final ValueClause valueClause) {
        this.tableName = tableName;
        this.columnClause = columnClause;
        this.valueClause = valueClause;
    }

    public static InsertQueryBuilder from(final Object object) {
        final EntityMappingTable entityMappingTable = EntityMappingTable.from(object.getClass());

        return new InsertQueryBuilder(
                entityMappingTable.getTableName(),
                ColumnClause.from(entityMappingTable.getDomainTypes()),
                ValueClause.from(object, entityMappingTable.getDomainTypes()));
    }

    public String toSql() {
        return String.format(FORMAT, tableName, columnClause.toSql(), valueClause.toSql());
    }
}
