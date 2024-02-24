package persistence.sql.dml.query.builder;

import persistence.sql.dml.query.clause.WhereClause;
import persistence.sql.entity.model.DomainType;

import java.util.HashMap;
import java.util.Map;

public class DeleteQueryBuilder {

    private static final String FORMAT = "DELETE FROM %s %s";

    private final String tableName;
    private final WhereClause whereClause;

    private DeleteQueryBuilder(final String tableName,
                               final WhereClause whereClause) {
        this.tableName = tableName;
        this.whereClause = whereClause;
    }

    public String toSql() {
        return String.format(FORMAT,
                tableName,
                whereClause.toSql()
        );
    }

    public static DeleteQueryBuilder from(String tableName) {
        return new DeleteQueryBuilder(
                tableName,
                new WhereClause(new HashMap<>())
        );
    }

    public static DeleteQueryBuilder of(String tableName, Map<DomainType, String> where) {
        return new DeleteQueryBuilder(
                tableName,
                new WhereClause(where)
        );
    }

}
