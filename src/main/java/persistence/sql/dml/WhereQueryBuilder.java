package persistence.sql.dml;

import persistence.sql.metadata.ColumnsMetadata;

import java.util.List;

public class WhereQueryBuilder {

    private static final String WHERE_CLAUSE_TEMPLATE = "WHERE %s";
    private final WhereConditions whereConditions;

    public WhereQueryBuilder(ColumnsMetadata columns, List<WhereRecord> whereRecords) {
        this.whereConditions = new WhereConditions(whereRecords.stream()
                .map(whereRecord -> new WhereCondition(columns.getColumn(whereRecord.name()), whereRecord.operator(), whereRecord.value()))
                .toList());
    }

    public String generateWhereClausesQuery() {
        return String.format(WHERE_CLAUSE_TEMPLATE, whereConditions.generateWhereClausesQuery());
    }
}
