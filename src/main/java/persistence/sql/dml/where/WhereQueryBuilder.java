package persistence.sql.dml.where;

import java.util.List;

public class WhereQueryBuilder {

    private WhereQueryBuilder() {
    }

    public static WhereQueryBuilder builder() {
        return new WhereQueryBuilder();
    }

    public FetchWhereQuery and(List<WhereQuery> whereQueries) {
        return new FetchWhereQuery(whereQueries, WhereClauseType.AND);
    }

    public FetchWhereQuery or(List<WhereQuery> whereQueries) {
        return new FetchWhereQuery(whereQueries, WhereClauseType.OR);
    }
}
