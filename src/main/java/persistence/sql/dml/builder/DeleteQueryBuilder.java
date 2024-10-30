package persistence.sql.dml.builder;

import persistence.dialect.Dialect;
import persistence.metadata.TableName;
import persistence.metadata.WhereCondition;

import java.util.List;

import static persistence.clause.QueryClause.whereClause;

public class DeleteQueryBuilder {
    private static final String DELETE_FROM = "delete from";

    private final Dialect dialect;
    private final StringBuilder queryString;

    private DeleteQueryBuilder(Dialect dialect) {
        this.dialect = dialect;
        this.queryString = new StringBuilder();
    }

    public static DeleteQueryBuilder builder(Dialect dialect) {
        return new DeleteQueryBuilder(dialect);
    }

    public String build() {
        return queryString.toString();
    }

    public DeleteQueryBuilder delete(TableName table) {
        queryString.append( DELETE_FROM )
                .append( " " )
                .append( table.name() );
        return this;
    }

    public DeleteQueryBuilder where(List<WhereCondition> whereConditions) {
        queryString.append( whereClause(whereConditions) );
        return this;
    }
}
