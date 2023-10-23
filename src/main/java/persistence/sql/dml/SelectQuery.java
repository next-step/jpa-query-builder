package persistence.sql.dml;

import persistence.sql.dml.clause.WhereClause;
import persistence.sql.dml.clause.operator.WhereClauseSQLBuilder;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SelectQuery {
    public static final String ALL_SELECT_COLUMNS = "*";
    private Set<String> selectClause;
    private WhereClause whereClause;


    /**
     * TODO
     private OrderByClause orderByClause;
     private GroupByClause groupByClause;
     private HavingClause havingClause;
     */

    private SelectQuery(String... selectClause) {
        this.selectClause = Arrays.stream(selectClause)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    public SelectQuery where(WhereClause whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public String toSQLSelectClause() {
        if(selectClause.isEmpty()) {
            return ALL_SELECT_COLUMNS;
        }

        return String.join(", ", selectClause);
    }

    public String toSQLWhereClause() {
        return new WhereClauseSQLBuilder(whereClause).build();
    }

    public static SelectQuery select(String... selectClause) {
        return new SelectQuery(selectClause);
    }
}
