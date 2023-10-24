package persistence.sql.dml;

import persistence.sql.dml.clause.WhereClause;
import persistence.sql.dml.clause.operator.WhereClauseSQLBuilder;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class DeleteQuery {
    private WhereClause whereClause;

    private DeleteQuery() {}

    public DeleteQuery where(WhereClause whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    public String toSQLWhereClause() {
        return new WhereClauseSQLBuilder(whereClause).build();
    }

    public static DeleteQuery create() {
        return new DeleteQuery();
    }
}
