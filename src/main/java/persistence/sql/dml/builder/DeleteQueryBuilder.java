package persistence.sql.dml.builder;

import persistence.sql.dml.column.DmlColumn;
import persistence.sql.dml.statement.QueryStatement;

public class DeleteQueryBuilder {
    public static final DeleteQueryBuilder INSTANCE = new DeleteQueryBuilder();

    private DeleteQueryBuilder() {
    }

    public String delete(Object entity) {
        Class<?> clazz = entity.getClass();

        return QueryStatement.delete(clazz)
                .where(DmlColumn.id(clazz, entity))
                .query();
    }
}
