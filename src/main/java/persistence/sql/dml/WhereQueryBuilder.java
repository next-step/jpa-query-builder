package persistence.sql.dml;

import persistence.sql.dml.conditions.WhereCondition;
import persistence.sql.dml.conditions.WhereConditions;
import persistence.sql.dml.conditions.WhereRecord;
import persistence.sql.metadata.ColumnsMetadata;

import java.util.List;

public class WhereQueryBuilder {

    private static final String WHERE_CLAUSE_TEMPLATE = "WHERE %s";
    private final WhereConditions whereConditions;

    public WhereQueryBuilder(ColumnsMetadata columns, List<WhereRecord> whereRecords) {
        this.whereConditions = WhereConditions.of(whereRecords.stream()
                .map(whereRecord -> WhereCondition.of(columns.getColumn(whereRecord.name()), whereRecord.operator(), whereRecord.value()))
                .toList());
    }

    public String generateWhereClausesQuery() {
        return String.format(WHERE_CLAUSE_TEMPLATE, whereConditions.generateWhereClausesQuery());
    }
}
