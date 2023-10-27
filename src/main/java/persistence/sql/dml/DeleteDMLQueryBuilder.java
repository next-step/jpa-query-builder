package persistence.sql.dml;

import persistence.sql.dbms.Dialect;
import persistence.sql.dml.clause.WhereClause;
import persistence.sql.dml.clause.operator.WhereClauseSQLBuilder;

public class DeleteDMLQueryBuilder<E> extends DMLQueryBuilder<E> {
    private WhereClause whereClause;

    public DeleteDMLQueryBuilder(Dialect dialect, Class<E> entityClass) {
        super(dialect, entityClass);
    }

    public DeleteDMLQueryBuilder<E> where(WhereClause whereClause) {
        this.whereClause = whereClause;
        return this;
    }

    @Override
    public String build() {
        return String.format("DELETE FROM" +
                        " %s \n " +
                        " %s" +
                        ";",
                createTableNameDefinition(),
                createWhereSql()
        );
    }

    private String createWhereSql() {
        if (whereClause == null) {
            return "";
        }

        return new WhereClauseSQLBuilder(whereClause).build();
    }
}
