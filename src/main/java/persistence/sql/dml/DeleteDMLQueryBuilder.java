package persistence.sql.dml;

import persistence.sql.dbms.DbmsStrategy;

public class DeleteDMLQueryBuilder<E> extends DMLQueryBuilder<E> {
    private DeleteQuery query;

    public DeleteDMLQueryBuilder(DbmsStrategy dbmsStrategy, Class<E> entityClass, DeleteQuery query) {
        super(dbmsStrategy, entityClass);
        this.query = query;
    }

    @Override
    public String build() {
        return String.format("DELETE FROM" +
                        " %s \n " +
                        " %s" +
                        ";",
                createTableNameDefinition(),
                query.toSQLWhereClause()
        );
    }
}
