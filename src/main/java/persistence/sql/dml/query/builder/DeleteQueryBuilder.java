package persistence.sql.dml.query.builder;

import persistence.sql.dml.query.clause.WhereClause;
import persistence.sql.entity.conditional.Criterias;

public class DeleteQueryBuilder {

    private static final String FORMAT = "DELETE FROM %s %s";

    private final String tableName;
    private final WhereClause whereClause;

    private DeleteQueryBuilder(final String tableName,
                               final WhereClause whereClause) {
        this.tableName = tableName;
        this.whereClause = whereClause;
    }

    public static DeleteQueryBuilder from(String tableName) {
        return new DeleteQueryBuilder(
                tableName,
                new WhereClause(Criterias.emptyInstance())
        );
    }

    public static DeleteQueryBuilder of(String tableName, Criterias criterias) {
        return new DeleteQueryBuilder(
                tableName,
                new WhereClause(criterias)
        );
    }

    public String toSql() {
        return String.format(FORMAT,
                tableName,
                whereClause.toSql()
        );
    }


}
