package persistence.sql.dml.where;

import java.util.ArrayList;
import java.util.List;

public class WhereQueryBuilder {

    private final List<FetchWhereQuery> fetchWhereQueries;

    private WhereQueryBuilder() {
        this.fetchWhereQueries = new ArrayList<>();
    }

    public static WhereQueryBuilder builder() {
        return new WhereQueryBuilder();
    }

    public WhereQueryBuilder and(List<WhereQuery> whereQueries) {
        fetchWhereQueries.add(new FetchWhereQuery(whereQueries, WhereClauseType.AND));
        return this;
    }

    public WhereQueryBuilder or(List<WhereQuery> whereQueries) {
        fetchWhereQueries.add(new FetchWhereQuery(whereQueries, WhereClauseType.OR));
        return this;
    }

    public FetchWhereQueries build() {
        return new FetchWhereQueries(fetchWhereQueries);
    }
}
