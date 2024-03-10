package persistence.sql.dml;

import java.util.List;
import java.util.stream.Collectors;

public class WhereConditions {
    public static final String AND = " AND ";
    private final List<WhereCondition> whereConditions;

    public WhereConditions(List<WhereCondition> whereConditions) {
        this.whereConditions = whereConditions;
    }

    public String generateWhereClausesQuery() {
        return whereConditions.stream()
                .map(WhereCondition::toSqlCondition)
                .collect(Collectors.joining(AND));
    }
}
