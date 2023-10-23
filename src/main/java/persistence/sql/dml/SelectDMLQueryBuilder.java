package persistence.sql.dml;

import persistence.sql.dbms.DbmsStrategy;

import java.util.stream.Collectors;

public class SelectDMLQueryBuilder<E> extends DMLQueryBuilder<E> {
    private SelectQuery query;

    public SelectDMLQueryBuilder(DbmsStrategy dbmsStrategy, Class<E> entityClass, SelectQuery query) {
        super(dbmsStrategy, entityClass);
        this.query = query;
    }

    @Override
    public String build() {
        return String.format("SELECT %s \n" +
                        " FROM %s \n " +
                        " %s" +
                        ";",
                query.toSQLSelectClause(),
                createTableNameDefinition(),
                query.toSQLWhereClause()
        );
    }
}
