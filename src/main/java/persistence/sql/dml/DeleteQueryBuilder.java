package persistence.sql.dml;

import persistence.sql.QueryBuilder;
import persistence.sql.meta.Table;

public class DeleteQueryBuilder implements QueryBuilder {

    private static final String DELETE_DEFINITION = "DELETE FROM %s";

    private DeleteQueryBuilder() {
    }

    public static DeleteQueryBuilder from() {
        return new DeleteQueryBuilder();
    }

    @Override
    public String generateQuery(Object object) {
        Table table = Table.from((Class<?>) object);
        return String.format(DELETE_DEFINITION, table.getTableName());
    }
}
