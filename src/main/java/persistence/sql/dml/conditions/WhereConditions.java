package persistence.sql.dml.conditions;

import java.util.List;
import java.util.stream.Collectors;

public class WhereConditions {
    public static final String AND = " AND ";
    private final List<WhereCondition> whereConditions;

    private WhereConditions(List<WhereCondition> whereConditions) {
        this.whereConditions = whereConditions;
    }

    public static WhereConditions of(List<WhereCondition> whereConditions) {
        return new WhereConditions(whereConditions);
    }

    public String generateWhereClausesQuery() {
        return whereConditions.stream()
                .map(WhereCondition::toSqlCondition)
                .collect(Collectors.joining(AND));
    }
}
